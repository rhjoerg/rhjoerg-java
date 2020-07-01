package ch.rhjoerg.commons.tool;

import static ch.rhjoerg.commons.tool.ClassLoaders.contextClassLoader;
import static ch.rhjoerg.commons.tool.ClassLoaders.platformClassLoader;
import static ch.rhjoerg.commons.tool.ClassLoaders.systemClassLoader;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class ClassLoadersTests
{
	@Test
	public void test()
	{
		ClassLoader classLoader;

		classLoader = contextClassLoader();
		assertNotNull(classLoader);

		classLoader = platformClassLoader();
		assertNotNull(classLoader);

		classLoader = systemClassLoader();
		assertNotNull(classLoader);
	}
}
