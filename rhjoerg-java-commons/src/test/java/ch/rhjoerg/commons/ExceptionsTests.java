package ch.rhjoerg.commons;

import static ch.rhjoerg.commons.Exceptions.notYetImplemented;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class ExceptionsTests
{
	@Test
	public void testNotYetImplemented()
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PrintStream oldErr = System.err;

		System.setErr(new PrintStream(output, true, UTF_8));
		UnsupportedOperationException exception = notYetImplemented();
		System.setErr(oldErr);

		String expected = "not yet implemented: ch.rhjoerg.commons.ExceptionsTests.testNotYetImplemented";

		assertEquals(expected, exception.getMessage());
		assertEquals(expected, new String(output.toByteArray(), UTF_8).trim());
	}
}
