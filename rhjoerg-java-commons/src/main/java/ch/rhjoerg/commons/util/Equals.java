package ch.rhjoerg.commons.util;

import java.util.Arrays;

public interface Equals
{
	public static boolean equalObjects(Object a, Object b)
	{
		if (a == b)
		{
			return true;
		}

		if (a == null || b == null)
		{
			return false;
		}

		Class<?> aType = a.getClass();

		if (aType.isArray())
		{
			return equalArrays(a, b);
		}

		return a.equals(b);
	}

	public static boolean equalArrays(Object a, Object b)
	{
		if (a == b)
		{
			return true;
		}

		if (a == null || b == null)
		{
			return false;
		}

		Class<?> aType = a.getClass();
		Class<?> bType = b.getClass();

		if (aType.isArray() && bType.isArray())
		{
			Class<?> aComponentType = aType.getComponentType();
			Class<?> bComponentType = bType.getComponentType();

			if (aComponentType != bComponentType)
			{
				return false;
			}

			if (a instanceof boolean[])
			{
				return Arrays.equals((boolean[]) a, (boolean[]) b);
			}

			if (a instanceof byte[])
			{
				return Arrays.equals((byte[]) a, (byte[]) b);
			}

			if (a instanceof char[])
			{
				return Arrays.equals((char[]) a, (char[]) b);
			}

			if (a instanceof short[])
			{
				return Arrays.equals((short[]) a, (short[]) b);
			}

			if (a instanceof int[])
			{
				return Arrays.equals((int[]) a, (int[]) b);
			}

			if (a instanceof long[])
			{
				return Arrays.equals((long[]) a, (long[]) b);
			}

			if (a instanceof float[])
			{
				return Arrays.equals((float[]) a, (float[]) b);
			}

			if (a instanceof double[])
			{
				return Arrays.equals((double[]) a, (double[]) b);
			}

			Object[] aObjects = (Object[]) a;
			Object[] bObjects = (Object[]) b;

			return Arrays.deepEquals(aObjects, bObjects);
		}

		return false;
	}
}
