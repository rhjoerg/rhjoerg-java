package ch.rhjoerg.commons.util;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.stream.Stream;

public interface ToString
{
	public static String stringOfArray(Object array)
	{
		checkArgument(array == null || array.getClass().isArray());

		if (array == null)
		{
			return "null";
		}

		if (array instanceof boolean[])
		{
			return Arrays.toString((boolean[]) array);
		}

		if (array instanceof byte[])
		{
			return Arrays.toString((byte[]) array);
		}

		if (array instanceof char[])
		{
			return Arrays.toString((char[]) array);
		}

		if (array instanceof short[])
		{
			return Arrays.toString((short[]) array);
		}

		if (array instanceof int[])
		{
			return Arrays.toString((int[]) array);
		}

		if (array instanceof long[])
		{
			return Arrays.toString((long[]) array);
		}

		if (array instanceof float[])
		{
			return Arrays.toString((float[]) array);
		}

		if (array instanceof double[])
		{
			return Arrays.toString((double[]) array);
		}

		if (array instanceof String[])
		{
			return "[" + Stream.of((String[]) array).map(string -> "\"" + string + "\"").collect(joining(", ")) + "]";
		}

		return Arrays.toString((Object[]) array);
	}

	public static String stringOfObject(Object object)
	{
		if (object == null)
		{
			return "null";
		}

		if (object.getClass().isArray())
		{
			return stringOfArray(object);

		}

		if (object instanceof String)
		{
			return "\"" + object + "\"";
		}

		return object.toString();
	}
}
