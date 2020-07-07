package ch.rhjoerg.commons.function;

import static ch.rhjoerg.commons.Exceptions.toRuntimeException;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T>
{
	public void accept(T t) throws Exception;

	default ThrowingConsumer<T> andThen(ThrowingConsumer<? super T> after)
	{
		return (T t) ->
		{
			accept(t);
			after.accept(t);
		};
	}

	default Consumer<T> wrap()
	{
		return (t) ->
		{
			try
			{
				accept(t);
			}
			catch (Exception e)
			{
				throw toRuntimeException(e);
			}
		};
	}

	public static <T> Consumer<T> wrap(ThrowingConsumer<T> delegate)
	{
		return delegate.wrap();
	}
}
