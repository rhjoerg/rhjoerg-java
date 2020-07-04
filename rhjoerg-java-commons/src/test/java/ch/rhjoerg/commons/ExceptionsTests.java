package ch.rhjoerg.commons;

import static ch.rhjoerg.commons.Exceptions.notYetImplemented;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ExceptionsTests
{
	@Test
	public void testNotYetImplemented()
	{
		UnsupportedOperationException exception = notYetImplemented();

		assertEquals("ch.rhjoerg.commons.ExceptionsTests.testNotYetImplemented", exception.getMessage());
	}
}
