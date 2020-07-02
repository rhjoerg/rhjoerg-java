package ch.rhjoerg.commons.annotation;

import static ch.rhjoerg.commons.annotation.Annotations.annotationProxy;
import static ch.rhjoerg.commons.annotation.Annotations.findAnnotations;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ch.rhjoerg.commons.annotation.AnnotationsTests.TestAnnotation;

@TestAnnotation(foo = "foo")
public class AnnotationsTests
{
	@Test
	public void testAnnotationProxy() throws Exception
	{
		TestAnnotation a1 = AnnotationsTests.class.getAnnotation(TestAnnotation.class);
		TestAnnotation a2 = annotationProxy(TestAnnotation.class, Map.of("foo", "foo"));

		assertEquals("foo", a2.foo());
		assertEquals("bar", a2.bar());
		assertArrayEquals(new String[] { "foo", "bar" }, a2.foobar());

		assertEquals(a1, a2);
		assertEquals(a2, a1);

		assertEquals(a1.hashCode(), a2.hashCode());
		assertEquals("@TestAnnotation { bar = \"bar\", foo = \"foo\", foobar = [\"foo\", \"bar\"] }", a2.toString());
	}

	@Test
	public void testFindAnnotations()
	{
		List<TestAnnotation> annotations = findAnnotations(TestAnnotation.class, Foo.class);

		assertEquals(2, annotations.size());
	}

	@Documented
	@Inherited
	@Retention(RUNTIME)
	public static @interface TestAnnotation
	{
		String foo();

		String bar() default "bar";

		String[] foobar() default { "foo", "bar" };
	}

	@Documented
	@Inherited
	@Retention(RUNTIME)
	@TestAnnotation(foo = "foo")
	public static @interface TestAnnotation2
	{
	}

	@TestAnnotation(foo = "foo")
	@TestAnnotation2
	public static class Foo implements Serializable
	{
		private static final long serialVersionUID = 1L;
	}
}
