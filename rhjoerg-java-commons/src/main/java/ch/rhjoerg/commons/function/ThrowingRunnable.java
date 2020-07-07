package ch.rhjoerg.commons.function;

import static ch.rhjoerg.commons.Exceptions.toRuntimeException;

@FunctionalInterface
public interface ThrowingRunnable
{
	public void run() throws Exception;

	default Runnable wrap()
	{
		return () ->
		{
			try
			{
				run();
			}
			catch (Exception e)
			{
				throw toRuntimeException(e);
			}
		};
	}

	public static Runnable wrap(ThrowingRunnable delegate)
	{
		return delegate.wrap();
	}
}
