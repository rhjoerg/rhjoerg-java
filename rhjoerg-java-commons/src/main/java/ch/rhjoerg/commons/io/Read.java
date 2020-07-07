package ch.rhjoerg.commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import ch.rhjoerg.commons.function.ThrowingSupplier;

public interface Read
{
	public static <R> R read(InputStream input, Handler<R> handler) throws Exception
	{
		byte[] buffer = new byte[0x10000];

		for (;;)
		{
			int len = input.read(buffer);

			if (len > 0)
			{
				handler.accept(buffer, len);
			}
			else
			{
				int val = input.read();

				if (val < 0)
				{
					break;
				}

				buffer[0] = (byte) val;
				handler.accept(buffer, 1);
			}
		}

		return handler.get();
	}

	public static <R> R read(ThrowingSupplier<InputStream> open, Handler<R> handler) throws Exception
	{
		try (InputStream input = open.get())
		{
			return read(input, handler);
		}
	}

	public static <R> R read(Path path, Handler<R> handler) throws Exception
	{
		return read(() -> Files.newInputStream(path), handler);
	}

	public static <R> R read(File file, Handler<R> handler) throws Exception
	{
		return read(() -> new FileInputStream(file), handler);
	}

	public static <R> R read(URL url, Handler<R> handler) throws Exception
	{
		return read(() -> url.openStream(), handler);
	}

	public static byte[] bytes(InputStream input) throws Exception
	{
		return read(input, new BytesHandler());
	}

	public static byte[] bytes(ThrowingSupplier<InputStream> open) throws Exception
	{
		return read(open, new BytesHandler());
	}

	public static byte[] bytes(File file) throws Exception
	{
		return read(file, new BytesHandler());
	}

	public static byte[] bytes(Path path) throws Exception
	{
		return read(path, new BytesHandler());
	}

	public static byte[] bytes(URL url) throws Exception
	{
		return read(url, new BytesHandler());
	}

	public static String string(InputStream input, Charset charset) throws Exception
	{
		return read(input, new StringHandler(charset));
	}

	public static String string(ThrowingSupplier<InputStream> open, Charset charset) throws Exception
	{
		return read(open, new StringHandler(charset));
	}

	public static String string(File file, Charset charset) throws Exception
	{
		return read(file, new StringHandler(charset));
	}

	public static String string(Path path, Charset charset) throws Exception
	{
		return read(path, new StringHandler(charset));
	}

	public static String string(URL url, Charset charset) throws Exception
	{
		return read(url, new StringHandler(charset));
	}

	public static interface Handler<R>
	{
		public void accept(byte[] buffer, int len) throws Exception;

		public R get();
	}

	public static class BytesHandler implements Handler<byte[]>
	{
		private final ArrayList<byte[]> buffers = new ArrayList<>();

		@Override
		public void accept(byte[] buffer, int len) throws Exception
		{
			buffers.add(Arrays.copyOf(buffer, len));
		}

		@Override
		public byte[] get()
		{
			int len = buffers.stream().mapToInt(b -> b.length).sum();
			byte[] result = new byte[len];
			int off = 0;

			for (byte[] buffer : buffers)
			{
				System.arraycopy(buffer, 0, result, off, buffer.length);
				off += buffer.length;
			}

			return result;
		}
	}

	public static class StringHandler implements Handler<String>
	{
		private final Charset charset;
		private final BytesHandler delegate = new BytesHandler();

		public StringHandler(Charset charset)
		{
			this.charset = charset;
		}

		@Override
		public void accept(byte[] buffer, int len) throws Exception
		{
			delegate.accept(buffer, len);
		}

		@Override
		public String get()
		{
			return new String(delegate.get(), charset);
		}
	}
}
