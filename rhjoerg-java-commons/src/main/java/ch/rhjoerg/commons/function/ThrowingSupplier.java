package ch.rhjoerg.commons.function;

import static ch.rhjoerg.commons.Exceptions.toRuntimeException;

import java.util.function.Supplier;

public interface ThrowingSupplier<R>
{
	public R get() throws Exception;

	default Supplier<R> wrap()
	{
		return () ->
		{
			try
			{
				return get();
			}
			catch (Exception e)
			{
				throw toRuntimeException(e);
			}
		};
	}

	public static <R> Supplier<R> wrap(ThrowingSupplier<R> delegate)
	{
		return delegate.wrap();
	}
}
