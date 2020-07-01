package ch.rhjoerg.commons.reflect;

import static ch.rhjoerg.commons.util.Streams.stream;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public interface Packages
{
	public static boolean isSubPackageOf(String pkg, String parentPkg)
	{
		return pkg.startsWith(parentPkg + ".");
	}

	public static boolean isSubPackageOf(String pkg, Iterable<String> parentPkgs)
	{
		return stream(parentPkgs).anyMatch(parentPkg -> isSubPackageOf(pkg, parentPkg));
	}

	public static SortedSet<String> normalizePackages(Iterable<String> packages)
	{
		SortedSet<String> result = new TreeSet<String>();

		packages.forEach(pkg ->
		{
			if (isSubPackageOf(pkg, result))
			{
				return;
			}

			for (Iterator<String> i = result.iterator(); i.hasNext();)
			{
				String parentPkg = i.next();

				if (isSubPackageOf(parentPkg, pkg))
				{
					i.remove();
				}
			}

			result.add(pkg);
		});

		return result;
	}

	public static SortedSet<String> normalizePackages(String... packages)
	{
		return normalizePackages(List.of(packages));
	}
}
