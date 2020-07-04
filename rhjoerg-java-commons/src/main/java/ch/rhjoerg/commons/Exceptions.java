package ch.rhjoerg.commons;

public interface Exceptions
{
	public static RuntimeException toRuntimeException(Throwable t)
	{
		if (t == null)
		{
			return new RuntimeException();
		}

		if (t instanceof RuntimeException)
		{
			return (RuntimeException) t;
		}

		return new RuntimeException(t);
	}

	public static UnsupportedOperationException notYetImplemented()
	{
		Exception exception = new Exception();
		StackTraceElement caller = exception.getStackTrace()[1];
		String message = "not yet implemented: " + caller.getClassName() + "." + caller.getMethodName();

		System.err.println(message);

		return new UnsupportedOperationException(message);
	}
}
