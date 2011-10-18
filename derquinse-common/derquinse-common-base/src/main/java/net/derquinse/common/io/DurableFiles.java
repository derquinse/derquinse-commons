/*
 * Copyright (C) the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.derquinse.common.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import com.google.common.annotations.Beta;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;

/**
 * Provides utility methods for working with durable files.
 * All method parameters must be non-null unless documented otherwise.
 * @author Andres Rodriguez
 */
@Beta
public final class DurableFiles {
	/** Not instantiable. */
	private DurableFiles() {
		throw new AssertionError();
	}

	/**
	 * Copies to a file all bytes from an {@link InputStream} supplied by a factory. The file is
	 * sync'd before being closed.
	 * @param from the input factory
	 * @param to the destination file
	 * @throws IOException if an I/O error occurs
	 */
	public static void copy(InputSupplier<? extends InputStream> from, File to) throws IOException {
		boolean threw = true;
		InputStream in = from.getInput();
		try {
			FileOutputStream os = new FileOutputStream(to);
			try {
				ByteStreams.copy(in, os);
				os.flush();
				os.getFD().sync();
				threw = false;
			} finally {
				Closeables.close(os, threw);
			}
		} finally {
			Closeables.close(in, threw);
		}
	}

	/**
	 * Overwrites a file with the contents of a byte array.The file is sync'd before being closed.
	 * @param from the bytes to write
	 * @param to the destination file
	 * @throws IOException if an I/O error occurs
	 */
	public static void write(byte[] from, File to) throws IOException {
		copy(ByteStreams.newInputStreamSupplier(from), to);
	}

	/**
	 * Copies all the bytes from one file to another.The file is sync'd before being closed.
	 * @param from the source file
	 * @param to the destination file
	 * @throws IOException if an I/O error occurs
	 */
	public static void copy(File from, File to) throws IOException {
		copy(Files.newInputStreamSupplier(from), to);
	}

	/**
	 * Copies to a file all characters from a {@link Readable} and {@link Closeable} object supplied
	 * by a factory, using the given character set. The file is sync'd before being closed.
	 * @param from the readable supplier
	 * @param to the destination file
	 * @param charset the character set used when writing the file
	 * @throws IOException if an I/O error occurs
	 */
	public static <R extends Readable & Closeable> void copy(InputSupplier<R> from, File to, Charset charset)
			throws IOException {
		boolean threw = true;
		FileOutputStream os = new FileOutputStream(to);
		try {
			OutputStreamWriter w = new OutputStreamWriter(os, charset);
			try {
				CharStreams.copy(from, w);
				w.flush();
				os.flush();
				os.getFD().sync();
				threw = false;
			} finally {
				Closeables.close(w, threw);
			}
		} finally {
			Closeables.close(os, threw);
		}
	}

	/**
	 * Writes a character sequence (such as a string) to a file using the given character set. The
	 * file is sync'd before being closed.
	 * @param from the character sequence to write
	 * @param to the destination file
	 * @param charset the character set used when writing the file
	 * @throws IOException if an I/O error occurs
	 */
	public static void write(CharSequence from, File to, Charset charset) throws IOException {
		write(from, to, charset, false);
	}

	/**
	 * Appends a character sequence (such as a string) to a file using the given character set. The
	 * file is sync'd before being closed.
	 * @param from the character sequence to append
	 * @param to the destination file
	 * @param charset the character set used when writing the file
	 * @throws IOException if an I/O error occurs
	 */
	public static void append(CharSequence from, File to, Charset charset) throws IOException {
		write(from, to, charset, true);
	}

	/**
	 * Private helper method. Writes a character sequence to a file, optionally appending. The file is
	 * sync'd before being closed.
	 * @param from the character sequence to append
	 * @param to the destination file
	 * @param charset the character set used when writing the file
	 * @param append true to append, false to overwrite
	 * @throws IOException if an I/O error occurs
	 */
	private static void write(CharSequence from, File to, Charset charset, boolean append) throws IOException {
		boolean threw = true;
		FileOutputStream os = new FileOutputStream(to, append);
		try {
			OutputStreamWriter w = new OutputStreamWriter(os, charset);
			try {
				w.append(from);
				w.flush();
				os.flush();
				os.getFD().sync();
				threw = false;
			} finally {
				Closeables.close(w, threw);
			}
		} finally {
			Closeables.close(os, threw);
		}
	}
}
