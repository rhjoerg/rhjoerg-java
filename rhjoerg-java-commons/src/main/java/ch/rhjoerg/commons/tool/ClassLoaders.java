package ch.rhjoerg.commons.tool;

public interface ClassLoaders
{
	public static ClassLoader contextClassLoader()
	{
		return Thread.currentThread().getContextClassLoader();
	}

	public static ClassLoader platformClassLoader()
	{
		return ClassLoader.getPlatformClassLoader();
	}

	public static ClassLoader systemClassLoader()
	{
		return ClassLoader.getSystemClassLoader();
	}
}
