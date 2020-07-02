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
}
