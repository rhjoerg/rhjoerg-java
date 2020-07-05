package ch.rhjoerg.commons.tool;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeSet;

public class ExcludingClassLoader extends ClassLoader
{
	private static final Enumeration<URL> EMPTY_URLS = Collections.emptyEnumeration();

	private final TreeSet<String> exclusions = new TreeSet<>();

	public ExcludingClassLoader(ClassLoader parent, Collection<String> exclusions)
	{
		super(parent);

		this.exclusions.addAll(exclusions);
	}

	public ExcludingClassLoader(ClassLoader parent, String... exclusions)
	{
		this(parent, List.of(exclusions));
	}

	@Override
	protected URL findResource(String name)
	{
		if (exclusions.contains(name))
		{
			return null;
		}

		return getParent().getResource(name);
	}

	@Override
	protected Enumeration<URL> findResources(String name) throws IOException
	{
		if (exclusions.contains(name))
		{
			return EMPTY_URLS;
		}

		return getParent().getResources(name);
	}

	@Override
	public URL getResource(String name)
	{
		if (exclusions.contains(name))
		{
			return null;
		}

		return super.getResource(name);
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException
	{
		if (exclusions.contains(name))
		{
			return EMPTY_URLS;
		}

		return super.getResources(name);
	}
}
