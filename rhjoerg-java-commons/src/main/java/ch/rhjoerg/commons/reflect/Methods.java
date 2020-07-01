package ch.rhjoerg.commons.reflect;

import java.lang.reflect.Method;
import java.util.Arrays;

public interface Methods
{
	public static boolean isExpectedMethod(Method method, String expectedName, Class<?>... expectedParameterTypes)
	{
		return expectedName.equals(method.getName()) && Arrays.equals(expectedParameterTypes, method.getParameterTypes());
	}

	public static boolean isEqualsMethod(Method method)
	{
		return isExpectedMethod(method, "equals", Object.class);
	}

	public static boolean isHashCodeMethod(Method method)
	{
		return isExpectedMethod(method, "hashCode");
	}

	public static boolean isToStringMethod(Method method)
	{
		return isExpectedMethod(method, "toString");
	}
}
