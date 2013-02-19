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
package net.derquinse.common.util.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.derquinse.common.base.NotInstantiable;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;

/**
 * Provides utility methods for working with zip files. All method parameters must be non-null
 * unless documented otherwise.
 * @author Andres Rodriguez
 */
public final class ZipFiles extends NotInstantiable {
	/** Not instantiable. */
	private ZipFiles() {
	}

	/**
	 * Reads a zip file into memory.
	 * @param input Input data.
	 * @return A map from zip entry nada to entry data.
	 * @throws IOException if an I/O error occurs
	 */
	public static Map<String, byte[]> loadZip(InputStream input) throws IOException {
		ImmutableMap.Builder<String, byte[]> b = ImmutableMap.builder();
		final ZipInputStream zis = new ZipInputStream(input);
		ZipEntry entry;
		while ((entry = zis.getNextEntry()) != null) {
			final byte[] data = ByteStreams.toByteArray(zis);
			final String item = entry.getName();
			b.put(item, data);
			zis.closeEntry();
		}
		return b.build();
	}

	/**
	 * Reads a zip file into memory.
	 * @param input Input data.
	 * @return A map from zip entry nada to entry data.
	 * @throws IOException if an I/O error occurs
	 */
	public static Map<String, byte[]> loadZip(InputSupplier<? extends InputStream> input) throws IOException {
		InputStream is = input.getInput();
		try {
			return loadZip(is);
		} finally {
			Closeables.close(is, true);
		}
	}

	/** Compress and array of bytes with gzip. */
	public static byte[] gzip(byte[] input) {
		ByteArrayInputStream is = new ByteArrayInputStream(input);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
		try {
			try {
				GZIPOutputStream zos = new GZIPOutputStream(bos);
				try {
					ByteStreams.copy(is, zos);
				} finally {
					Closeables.close(zos, true);
				}
			} catch (IOException e) {
				// Should not happen.
				throw new RuntimeException(e);
			} finally {
				Closeables.close(is, true);
			}
		} catch (IOException e) {
			// Should not happen.
			throw new RuntimeException(e);
		}
		return bos.toByteArray();
	}

	/** Function version of {@link #gzip(byte[])}. */
	public static Function<byte[], byte[]> gzip() {
		return Gzip.INSTANCE;
	}

	private enum Gzip implements Function<byte[], byte[]> {
		INSTANCE;

		@Override
		public byte[] apply(byte[] input) {
			return gzip(input);
		}
	}

	/**
	 * Decompress and array of bytes with gzip.
	 * @throws IllegalArgumentException if unable to decompress the input.
	 */
	public static byte[] gunzip(byte[] input) {
		ByteArrayInputStream is = new ByteArrayInputStream(input);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
		try {
			try {
				GZIPInputStream zis = new GZIPInputStream(is);
				try {
					ByteStreams.copy(zis, bos);
				} finally {
					Closeables.close(zis, true);
				}
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			} finally {
				Closeables.close(bos, true);
			}
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		return bos.toByteArray();
	}

	/** Function version of {@link #gunzip(byte[])}. */
	public static Function<byte[], byte[]> gunzip() {
		return Gunzip.INSTANCE;
	}

	private enum Gunzip implements Function<byte[], byte[]> {
		INSTANCE;

		@Override
		public byte[] apply(byte[] input) {
			return gunzip(input);
		}
	}

	/**
	 * Compress the input with gzip if the input is longer than 128 bytes and the output is smaller
	 * than the input.
	 */
	public static MaybeCompressed<byte[]> maybeGzip(byte[] input) {
		if (input.length <= 128) {
			return MaybeCompressed.of(false, input);
		}
		byte[] compressed = gzip(input);
		if (compressed.length < input.length) {
			return MaybeCompressed.of(true, compressed);
		} else {
			return MaybeCompressed.of(false, input);
		}
	}

	/**
	 * Function version of {@link #maybeGzip(byte[])}.
	 */
	public static Function<byte[], MaybeCompressed<byte[]>> maybeGzip() throws IOException {
		return MaybeGzip.INSTANCE;
	}

	private enum MaybeGzip implements Function<byte[], MaybeCompressed<byte[]>> {
		INSTANCE;

		@Override
		public MaybeCompressed<byte[]> apply(byte[] input) {
			return maybeGzip(input);
		}
	}

	/**
	 * Reads a zip file into memory.
	 * @param file Input file.
	 * @return A map from zip entry nada to entry data.
	 * @throws IOException if an I/O error occurs
	 */
	public static Map<String, byte[]> loadZip(File file) throws IOException {
		return loadZip(Files.newInputStreamSupplier(file));
	}

	/**
	 * Reads a zip file into memory. Individual entries are Gzipped, and those smaller than the
	 * original will be kept in compressed form.
	 * @param input Input data.
	 * @return A map from zip entry nada to entry data, which may be compressed.
	 * @throws IOException if an I/O error occurs
	 */
	public static Map<String, MaybeCompressed<byte[]>> loadZipAndGZip(InputStream input) throws IOException {
		Map<String, byte[]> loaded = loadZip(input);
		return ImmutableMap.copyOf(Maps.transformValues(loaded, maybeGzip()));
	}

	/**
	 * Reads a zip file into memory. Individual entries are Gzipped, and those smaller than the
	 * original will be kept in compressed form.
	 * @param input Input data.
	 * @return A map from zip entry nada to entry data, which may be compressed.
	 * @throws IOException if an I/O error occurs
	 */
	public static Map<String, MaybeCompressed<byte[]>> loadZipAndGZip(InputSupplier<? extends InputStream> input)
			throws IOException {
		InputStream is = input.getInput();
		try {
			return loadZipAndGZip(is);
		} finally {
			Closeables.close(is, true);
		}
	}

	/**
	 * Reads a zip file into memory. Individual entries are Gzipped, and those smaller than the
	 * original will be kept in compressed form.
	 * @param file Input file.
	 * @return A map from zip entry nada to entry data, which may be compressed.
	 * @throws IOException if an I/O error occurs
	 */
	public static Map<String, MaybeCompressed<byte[]>> loadZipAndGZip(File file) throws IOException {
		return loadZipAndGZip(Files.newInputStreamSupplier(file));
	}

}
