package ch.rhjoerg.commons.function;

import static ch.rhjoerg.commons.Exceptions.toRuntimeException;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Throwable>
{
	public void accept(T t) throws E;

	default Consumer<T> wrap()
	{
		return (t) ->
		{
			try
			{
				accept(t);
			}
			catch (Throwable e)
			{
				throw toRuntimeException(e);
			}
		};
	}

	public static <T, E extends Throwable> Consumer<T> wrap(ThrowingConsumer<T, E> delegate)
	{
		return delegate.wrap();
	}
}
