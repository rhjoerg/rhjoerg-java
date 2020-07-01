package ch.rhjoerg.commons.annotation;

import static java.lang.reflect.Proxy.newProxyInstance;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Annotations
{
	public static <A extends Annotation> A annotationProxy(Class<A> annotationType, Map<String, Object> values)
	{
		ClassLoader loader = annotationType.getClassLoader();
		Class<?>[] interfaces = { annotationType };
		AnnotationHandler<A> handler = new AnnotationHandler<>(annotationType, values);

		return annotationType.cast(newProxyInstance(loader, interfaces, handler));
	}

	public static <A extends Annotation> List<A> findAnnotations(Class<A> annotationType, Class<?> element)
	{
		List<A> result = new ArrayList<>();
		HashSet<Class<?>> visited = new HashSet<>();
		boolean inherited = annotationType.isAnnotationPresent(Inherited.class);

		visited.add(annotationType);

		return findAnnotations(annotationType, element, visited, inherited, result);
	}

	private static <A extends Annotation> List<A> findAnnotations(Class<A> annotationType, Class<?> element, HashSet<Class<?>> visited, boolean inherited,
			List<A> result)
	{
		if (visited.contains(element))
		{
			return result;
		}

		visited.add(element);

		for (Annotation annotation : element.getDeclaredAnnotations())
		{
			Class<? extends Annotation> candidateType = annotation.annotationType();

			if (annotationType.equals(candidateType))
			{
				result.add(annotationType.cast(annotation));
			}
			else
			{
				findAnnotations(annotationType, candidateType, visited, false, result);
			}
		}

		if (inherited)
		{
			for (Class<?> iface : element.getInterfaces())
			{
				findAnnotations(annotationType, iface, visited, inherited, result);
			}

			Class<?> superclass = element.getSuperclass();

			if (superclass != null)
			{
				findAnnotations(annotationType, superclass, visited, inherited, result);
			}
		}

		return result;
	}

	public static <A extends Annotation> List<Map.Entry<Class<?>, List<A>>> findAnnotationDetails(Class<A> annotationType, Class<?> element)
	{
		List<Map.Entry<Class<?>, List<A>>> result = new ArrayList<>();
		HashSet<Class<?>> visited = new HashSet<>();
		boolean inherited = annotationType.isAnnotationPresent(Inherited.class);

		visited.add(annotationType);

		return findAnnotationDetails(annotationType, element, element, visited, inherited, result);
	}

	private static <A extends Annotation> List<Map.Entry<Class<?>, List<A>>> findAnnotationDetails(Class<A> annotationType, Class<?> element, Class<?> focus,
			HashSet<Class<?>> visited, boolean inherited, List<Map.Entry<Class<?>, List<A>>> result)
	{
		if (visited.contains(element))
		{
			return result;
		}

		visited.add(element);

		for (Annotation annotation : element.getDeclaredAnnotations())
		{
			Class<? extends Annotation> candidateType = annotation.annotationType();

			if (annotationType.equals(candidateType))
			{
				List<A> list = findAnnotationList(annotationType, focus, result);

				list.add(annotationType.cast(annotation));
			}
			else
			{
				findAnnotationDetails(annotationType, candidateType, focus, visited, false, result);
			}
		}

		if (inherited)
		{
			for (Class<?> iface : element.getInterfaces())
			{
				findAnnotationDetails(annotationType, iface, iface, visited, inherited, result);
			}

			Class<?> superclass = element.getSuperclass();

			if (superclass != null)
			{
				findAnnotationDetails(annotationType, superclass, superclass, visited, inherited, result);
			}
		}

		return result;
	}

	private static <A extends Annotation> List<A> findAnnotationList(Class<A> annotationType, Class<?> element, List<Map.Entry<Class<?>, List<A>>> result)
	{
		for (Map.Entry<Class<?>, List<A>> entry : result)
		{
			if (entry.getKey().equals(element))
			{
				return entry.getValue();
			}
		}

		List<A> list = new ArrayList<>();

		result.add(Map.entry(element, list));

		return list;
	}
}
