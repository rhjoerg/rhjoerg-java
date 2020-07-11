package ch.rhjoerg.commons.function;

import static ch.rhjoerg.commons.Exceptions.toRuntimeException;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R>
{
	public R apply(T t) throws Exception;

	default Function<T, R> wrap()
	{
		return (t) ->
		{
			try
			{
				return apply(t);
			}
			catch (Exception e)
			{
				throw toRuntimeException(e);
			}
		};
	}

	public static <T, R> Function<T, R> wrap(ThrowingFunction<T, R> delegate)
	{
		return delegate.wrap();
	}
}
