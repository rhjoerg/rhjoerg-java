package ch.rhjoerg.commons.reflect;

import static ch.rhjoerg.commons.reflect.Packages.normalizePackages;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class PackagesTests
{
	@Test
	public void testNormalizePackages()
	{
		String[] input = { "a.c", "a", "b", "c.c", "a.d" };
		String[] expected = { "a", "b", "c.c" };
		String[] actual = normalizePackages(input).toArray(String[]::new);

		assertArrayEquals(expected, actual);
	}
}
