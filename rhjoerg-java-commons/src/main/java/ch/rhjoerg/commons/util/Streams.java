package ch.rhjoerg.commons.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Streams
{
	public static <T> Stream<T> stream(Iterable<T> iterable, boolean parallel)
	{
		return StreamSupport.stream(iterable.spliterator(), parallel);
	}

	public static <T> Stream<T> stream(Iterable<T> iterable)
	{
		return stream(iterable, false);
	}
}
