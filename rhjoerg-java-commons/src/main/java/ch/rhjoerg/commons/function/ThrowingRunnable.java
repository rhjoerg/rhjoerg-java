package ch.rhjoerg.commons.function;

import static ch.rhjoerg.commons.Exceptions.toRuntimeException;

@FunctionalInterface
public interface ThrowingRunnable<E extends Throwable>
{
	public void run() throws E;

	default Runnable wrap()
	{
		return () ->
		{
			try
			{
				run();
			}
			catch (Throwable e)
			{
				throw toRuntimeException(e);
			}
		};
	}

	public static <E extends Throwable> Runnable wrap(ThrowingRunnable<E> delegate)
	{
		return delegate.wrap();
	}
}
