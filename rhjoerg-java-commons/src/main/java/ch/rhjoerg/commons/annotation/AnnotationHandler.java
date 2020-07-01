package ch.rhjoerg.commons.annotation;

import static ch.rhjoerg.commons.Exceptions.toRuntimeException;
import static ch.rhjoerg.commons.reflect.Methods.isEqualsMethod;
import static ch.rhjoerg.commons.reflect.Methods.isExpectedMethod;
import static ch.rhjoerg.commons.reflect.Methods.isHashCodeMethod;
import static ch.rhjoerg.commons.reflect.Methods.isToStringMethod;
import static ch.rhjoerg.commons.util.Equals.equalObjects;
import static ch.rhjoerg.commons.util.HashCode.hashCodeOfObject;
import static ch.rhjoerg.commons.util.ToString.stringOfObject;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class AnnotationHandler<A extends Annotation> implements InvocationHandler
{
	private static final String ILLEGAL_TYPE_TEMPLATE = "%s isn't an annotation";
	private static final String ILLEGAL_ARGUMENT_COUNT_TEMPLATE = "%s call has illegal argument count";
	private static final String INCOMPLETE_ANNOTATION_TEMPLATE = "incomplete annotation (call of %s)";

	private static final Comparator<Method> METHOD_COMPARATOR = (m1, m2) -> m1.getName().compareTo(m2.getName());

	private final Class<A> type;
	private final TreeMap<String, Object> values = new TreeMap<>();

	public AnnotationHandler(Class<A> type, Map<String, Object> values)
	{
		this.type = checkType(type);
		this.values.putAll(values);
	}

	private static <T extends Annotation> Class<T> checkType(Class<T> type)
	{
		checkArgument(type.isAnnotation(), ILLEGAL_TYPE_TEMPLATE, type);

		Class<?>[] superInterfaces = type.getInterfaces();

		checkArgument(superInterfaces.length == 1, ILLEGAL_TYPE_TEMPLATE, type);
		checkArgument(Annotation.class == superInterfaces[0], ILLEGAL_TYPE_TEMPLATE, type);

		return type;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
	{
		if (isEqualsMethod(method))
		{
			checkArgument(args.length == 1, ILLEGAL_ARGUMENT_COUNT_TEMPLATE, method);
			return equals(proxy, args[0]);
		}

		checkArgument(method.getParameterCount() == 0, ILLEGAL_ARGUMENT_COUNT_TEMPLATE, method);
		checkArgument(args == null || args.length == 0, ILLEGAL_ARGUMENT_COUNT_TEMPLATE, method);

		if (isHashCodeMethod(method))
		{
			return hashCode(proxy);
		}

		if (isToStringMethod(method))
		{
			return toString(proxy);
		}

		if (isExpectedMethod(method, "annotationType"))
		{
			return type;
		}

		Object result = values.get(method.getName());

		if (result == null)
		{
			result = method.getDefaultValue();
		}

		checkState(result != null, INCOMPLETE_ANNOTATION_TEMPLATE, method);

		return result;
	}

	private boolean equals(Object proxy, Object object)
	{
		if (proxy == object)
		{
			return true;
		}

		if (proxy == null || object == null)
		{
			return false;
		}

		if (!type.isAssignableFrom(object.getClass()))
		{
			return false;
		}

		return methods().stream().filter(method -> notEquals(proxy, object, method)).findFirst().isEmpty();
	}

	private boolean notEquals(Object proxy, Object other, Method method)
	{
		try
		{
			Object a = method.invoke(proxy);
			Object b = method.invoke(proxy);

			return !equalObjects(a, b);
		}
		catch (Exception e)
		{
			throw toRuntimeException(e);
		}
	}

	/**
	 * See {@link sun.reflect.annotation.AnnotationInvocationHandler#hashCodeImpl() AnnotationInvocationHandler.hashCodeImpl()} for the original code.
	 */
	private int hashCode(Object proxy)
	{
		IntSummaryStatistics result = new IntSummaryStatistics();

		values(proxy).forEach((key, value) ->
		{
			result.accept((127 * key.hashCode()) ^ hashCodeOfObject(value));
		});

		return (int) result.getSum();
	}

	private String toString(Object proxy)
	{
		StringBuilder sb = new StringBuilder();
		List<String> content = new ArrayList<>();

		sb.append('@').append(type.getSimpleName()).append(" {");

		values(proxy).forEach((key, value) -> content.add(key + " = " + stringOfObject(value)));

		if (!content.isEmpty())
		{
			sb.append(' ');
			sb.append(content.stream().collect(joining(", ")));
			sb.append(' ');
		}

		sb.append("}");

		return sb.toString();
	}

	private List<Method> methods()
	{
		return Stream.of(type.getMethods()) //
				.filter(method -> !isEqualsMethod(method)) //
				.filter(method -> !isHashCodeMethod(method)) //
				.filter(method -> !isToStringMethod(method)) //
				.filter(method -> !isExpectedMethod(method, "annotationType")) //
				.sorted(METHOD_COMPARATOR) //
				.collect(toList());
	}

	private Map<String, Object> values(Object proxy)
	{
		TreeMap<String, Object> values = new TreeMap<>();

		methods().forEach(method ->
		{
			values.put(method.getName(), value(proxy, method));
		});

		return values;
	}

	private Object value(Object proxy, Method method)
	{
		try
		{
			return method.invoke(proxy);
		}
		catch (Exception e)
		{
			throw toRuntimeException(e);
		}
	}
}
