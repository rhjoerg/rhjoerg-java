package ch.rhjoerg.commons.util;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Arrays;

public interface HashCode
{
	public static int hashCodeOfArray(Object array)
	{
		checkArgument(array == null || array.getClass().isArray());

		if (array == null)
		{
			return 0;
		}

		if (array instanceof boolean[])
		{
			return Arrays.hashCode((boolean[]) array);
		}

		if (array instanceof byte[])
		{
			return Arrays.hashCode((byte[]) array);
		}

		if (array instanceof char[])
		{
			return Arrays.hashCode((char[]) array);
		}

		if (array instanceof short[])
		{
			return Arrays.hashCode((short[]) array);
		}

		if (array instanceof int[])
		{
			return Arrays.hashCode((int[]) array);
		}

		if (array instanceof long[])
		{
			return Arrays.hashCode((long[]) array);
		}

		if (array instanceof float[])
		{
			return Arrays.hashCode((float[]) array);
		}

		if (array instanceof double[])
		{
			return Arrays.hashCode((double[]) array);
		}

		return Arrays.hashCode((Object[]) array);
	}

	public static int hashCodeOfObject(Object object)
	{
		if (object == null)
		{
			return 0;
		}

		if (object.getClass().isArray())
		{
			return hashCodeOfArray(object);
		}

		return object.hashCode();
	}
}
