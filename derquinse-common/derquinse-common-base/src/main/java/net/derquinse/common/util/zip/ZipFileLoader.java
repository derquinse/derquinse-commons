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

import static com.google.common.base.Preconditions.checkArgument;
import static net.derquinse.common.util.zip.InternalPreconditions.checkInput;
import static net.derquinse.common.util.zip.InternalPreconditions.checkLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import net.derquinse.common.io.MaximumSizeExceededException;
import net.derquinse.common.io.MemoryByteSource;
import net.derquinse.common.io.MemoryByteSourceLoader;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.ByteSource;
import com.google.common.io.Closer;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.google.common.primitives.Ints;

/**
 * Zip file loader.
 * @author Andres Rodriguez
 */
public final class ZipFileLoader {
	/** Default memory loader. */
	private static final MemoryByteSourceLoader DEFAULT_LOADER = MemoryByteSourceLoader.get().chunkSize(16384);
	/** Default file loader. */
	private static final ZipFileLoader DEFAULT = new ZipFileLoader(DEFAULT_LOADER, Long.MAX_VALUE);

	/** Loader to use. */
	private final MemoryByteSourceLoader loader;
	/** Maximum loaded size. */
	private final long maxSize;

	/** Returns a zip file loader with a default memory loader (heap, chunked, 16 KB chunks). */
	public static ZipFileLoader get() {
		return DEFAULT;
	}

	/** Constructor. */
	private ZipFileLoader(MemoryByteSourceLoader loader, long maxSize) {
		this.loader = checkLoader(loader);
		this.maxSize = maxSize;
	}

	/** Returns a zip file loader with the provided memory loader and the same maximum loaded size. */
	public ZipFileLoader loader(MemoryByteSourceLoader loader) {
		checkLoader(loader);
		if (loader.equals(this.loader)) {
			return this;
		}
		return new ZipFileLoader(loader, maxSize);
	}

	/** Returns a zip file loader with the same memory loader and the provided maximum loaded size. */
	public ZipFileLoader maxSize(long maxSize) {
		checkArgument(maxSize > 0, "The maximum loaded size must be greater than 0");
		if (maxSize == this.maxSize) {
			return this;
		}
		return new ZipFileLoader(loader, maxSize);
	}

	/**
	 * Loads a zip file into memory.
	 * @param input Input data. The stream is not closed.
	 * @return The loaded zip file.
	 * @throws IOException if an I/O error occurs.
	 * @throws MaximumSizeExceededException if any of the entries exceeds the maximum size.
	 */
	public LoadedZipFile load(InputStream input) throws IOException {
		checkInput(input);
		ImmutableMap.Builder<String, MemoryByteSource> b = ImmutableMap.builder();
		MemoryByteSourceLoader currentLoader = loader;
		long allowed = maxSize;
		final ZipInputStream zis = new ZipInputStream(input);
		ZipEntry entry;
		while ((entry = zis.getNextEntry()) != null) {
			if (allowed < currentLoader.getMaxSize()) {
				currentLoader = currentLoader.maxSize(Ints.saturatedCast(allowed));
			}
			try {
				final MemoryByteSource data = currentLoader.load(zis);
				allowed -= data.size();
				final String item = entry.getName();
				b.put(item, data);
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(String.format("Maximum size of %d exceeded while decompressing", maxSize));
			} finally {
				zis.closeEntry();
			}
		}
		return new LoadedZipFile(loader, b.build());
	}

	/**
	 * Loads a zip file into memory.
	 * @param input Input data.
	 * @return The loaded zip file.
	 * @throws IOException if an I/O error occurs
	 * @throws MaximumSizeExceededException if any of the entries exceeds the maximum size.
	 */
	public LoadedZipFile load(InputSupplier<? extends InputStream> input) throws IOException {
		checkInput(input);
		Closer closer = Closer.create();
		try {
			return load(closer.register(input.getInput()));
		} finally {
			closer.close();
		}
	}

	/**
	 * Loads a zip file into memory.
	 * @param input Input data.
	 * @return The loaded zip file.
	 * @throws IOException if an I/O error occurs
	 * @throws MaximumSizeExceededException if any of the entries exceeds the maximum size.
	 */
	public LoadedZipFile load(ByteSource input) throws IOException {
		checkInput(input);
		Closer closer = Closer.create();
		try {
			return load(closer.register(input.openStream()));
		} finally {
			closer.close();
		}
	}

	/**
	 * Loads a zip file into memory.
	 * @param file Input file.
	 * @return A map from zip entry nada to entry data.
	 * @throws IOException if an I/O error occurs
	 * @throws MaximumSizeExceededException if any of the entries exceeds the maximum size.
	 */
	public LoadedZipFile load(File file) throws IOException {
		checkInput(file);
		return load(Files.newInputStreamSupplier(file));
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(loader, maxSize);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ZipFileLoader) {
			ZipFileLoader other = (ZipFileLoader) obj;
			return maxSize == other.maxSize && loader.equals(other.loader);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("maxSize", maxSize).add("loader", loader).toString();
	}

}
