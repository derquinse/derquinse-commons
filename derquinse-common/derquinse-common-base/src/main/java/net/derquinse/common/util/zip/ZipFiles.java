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
import java.util.Map.Entry;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.derquinse.common.base.NotInstantiable;

import com.google.common.collect.ImmutableMap;
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
			Closeables.closeQuietly(is);
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
	 * original will be kepts in compressed form.
	 * @param input Input data.
	 * @return A map from zip entry nada to entry data, which may be compressed.
	 * @throws IOException if an I/O error occurs
	 */
	public static Map<String, MaybeCompressed<byte[]>> loadZipAndGZip(InputStream input) throws IOException {
		Map<String, byte[]> loaded = loadZip(input);
		ImmutableMap.Builder<String, MaybeCompressed<byte[]>> b = ImmutableMap.builder();
		for (Entry<String, byte[]> e : loaded.entrySet()) {
			byte[] data = e.getValue();
			ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
			GZIPOutputStream zos = new GZIPOutputStream(bos);
			ByteArrayInputStream is = new ByteArrayInputStream(data);
			ByteStreams.copy(is, zos);
			Closeables.closeQuietly(is);
			Closeables.closeQuietly(zos);
			byte[] compressed = bos.toByteArray();
			final MaybeCompressed<byte[]> v;
			if (compressed.length < data.length) {
				v = MaybeCompressed.of(true, compressed);
			} else {
				v = MaybeCompressed.of(false, data);
			}
			b.put(e.getKey(), v);
		}
		return b.build();
	}

	/**
	 * Reads a zip file into memory. Individual entries are Gzipped, and those smaller than the
	 * original will be kepts in compressed form.
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
			Closeables.closeQuietly(is);
		}
	}

	/**
	 * Reads a zip file into memory. Individual entries are Gzipped, and those smaller than the
	 * original will be kepts in compressed form.
	 * @param input Input data.
	 * @return A map from zip entry nada to entry data, which may be compressed.
	 * @throws IOException if an I/O error occurs
	 */
	public static Map<String, MaybeCompressed<byte[]>> loadZipAndGZip(File file) throws IOException {
		return loadZipAndGZip(Files.newInputStreamSupplier(file));
	}

}
