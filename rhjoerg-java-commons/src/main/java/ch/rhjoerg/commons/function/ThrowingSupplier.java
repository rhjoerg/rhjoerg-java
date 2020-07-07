package ch.rhjoerg.commons.function;

import static ch.rhjoerg.commons.Exceptions.toRuntimeException;

import java.util.function.Supplier;

public interface ThrowingSupplier<R, E extends Throwable>
{
	public R get() throws E;

	default Supplier<R> wrap()
	{
		return () ->
		{
			try
			{
				return get();
			}
			catch (Throwable e)
			{
				throw toRuntimeException(e);
			}
		};
	}

	public static <R, E extends Throwable> Supplier<R> wrap(ThrowingSupplier<R, E> delegate)
	{
		return delegate.wrap();
	}
}
