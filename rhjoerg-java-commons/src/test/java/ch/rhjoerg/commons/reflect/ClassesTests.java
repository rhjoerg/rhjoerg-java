package ch.rhjoerg.commons.reflect;

import static ch.rhjoerg.commons.reflect.Classes.walkClassTree;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ClassesTests
{
	@Test
	public void testWalkClassTree()
	{
		List<Class<?>> expected = List.of(Bar.class, Serializable.class, Foo.class, Serializable.class, Object.class);
		List<Class<?>> actual = new ArrayList<>();

		walkClassTree(Bar.class, type -> actual.add(type));

		assertEquals(expected, actual);
	}

	public static class Foo implements Serializable
	{
		private static final long serialVersionUID = 1L;
	}

	public static class Bar extends Foo implements Serializable
	{
		private static final long serialVersionUID = 1L;
	}
}
