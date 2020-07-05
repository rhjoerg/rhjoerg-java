package ch.rhjoerg.commons.tool;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ExcludingClassLoaderTests
{
	@Test
	public void test() throws Exception
	{
		String excludedName = ExcludingClassLoaderTests.class.getName().replace('.', '/') + ".txt";
		String includedName = ExcludingClassLoaderTests.class.getName().replace('.', '/') + ".class";

		ClassLoader parentClassLoader = ExcludingClassLoaderTests.class.getClassLoader();

		assertNotNull(ExcludingClassLoaderTests.class.getResource("ExcludingClassLoaderTests.txt"));
		assertNotNull(ExcludingClassLoaderTests.class.getResource("ExcludingClassLoaderTests.class"));

		assertTrue(parentClassLoader.getResources(excludedName).hasMoreElements());
		assertTrue(parentClassLoader.getResources(includedName).hasMoreElements());

		ExcludingClassLoader excludingClassLoader = new ExcludingClassLoader(parentClassLoader, excludedName);

		assertNull(excludingClassLoader.findResource(excludedName));
		assertNotNull(excludingClassLoader.findResource(includedName));

		assertNull(excludingClassLoader.getResource(excludedName));
		assertNotNull(excludingClassLoader.getResource(includedName));

		assertFalse(excludingClassLoader.findResources(excludedName).hasMoreElements());
		assertTrue(excludingClassLoader.findResources(includedName).hasMoreElements());

		assertFalse(excludingClassLoader.getResources(excludedName).hasMoreElements());
		assertTrue(excludingClassLoader.getResources(includedName).hasMoreElements());
	}
}
