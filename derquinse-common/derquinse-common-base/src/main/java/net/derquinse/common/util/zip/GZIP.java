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

import static net.derquinse.common.util.zip.InternalPreconditions.checkInput;
import static net.derquinse.common.util.zip.InternalPreconditions.checkLoader;
import static net.derquinse.common.util.zip.InternalPreconditions.checkOutput;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.derquinse.common.base.NotInstantiable;
import net.derquinse.common.io.ByteStreamTransformer;
import net.derquinse.common.io.BytesTransformer;
import net.derquinse.common.io.MemoryByteSource;
import net.derquinse.common.io.MemoryByteSourceLoader;

import com.google.common.base.Function;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.util.concurrent.UncheckedExecutionException;

/**
 * Provides utility methods for working with gzip streams. All method parameters must be non-null
 * unless documented otherwise.
 * @author Andres Rodriguez
 */
public final class GZIP extends NotInstantiable {
	/** Not instantiable. */
	private GZIP() {
	}

	/** Compression transformer. */
	private static final BytesTransformer COMPRESS = BytesTransformer.of(Transformers.COMPRESS);
	/** Decompression transformer. */
	private static final BytesTransformer DECOMPRESS = BytesTransformer.of(Transformers.DECOMPRESS);

	/** Returns a GZIP compression transformer. */
	public static BytesTransformer compression() {
		return COMPRESS;
	}

	/** Returns a GZIP decompression transformer. */
	public static BytesTransformer decompression() {
		return DECOMPRESS;
	}

	/** Byte transformers. */
	private enum Transformers implements ByteStreamTransformer {
		COMPRESS {
			@Override
			public void transform(InputStream input, OutputStream output) throws IOException {
				checkInput(input);
				checkOutput(output);
				GZIPOutputStream zos = new GZIPOutputStream(output);
				ByteStreams.copy(input, zos);
				zos.finish();
			}
		},
		DECOMPRESS {
			@Override
			public void transform(InputStream input, OutputStream output) throws IOException {
				checkInput(input);
				checkOutput(output);
				GZIPInputStream zis = new GZIPInputStream(input);
				ByteStreams.copy(zis, output);
			}
		};
	}

	/**
	 * Compress the input with gzip if the input is longer than 128 bytes and the output is smaller
	 * than the input.
	 */
	public static MaybeCompressed<MemoryByteSource> maybeGzip(MemoryByteSourceLoader loader, MemoryByteSource input)
			throws IOException {
		checkLoader(loader);
		checkInput(input);
		if (input.size() <= 128) {
			return MaybeCompressed.of(false, input);
		}
		MemoryByteSource compressed = loader.transformer(COMPRESS).load(input);
		if (compressed.size() < input.size()) {
			return MaybeCompressed.of(true, compressed);
		} else {
			return MaybeCompressed.of(false, input);
		}
	}

	/**
	 * Function version of {@link #maybeGzip(MemoryByteSourceLoader, MemoryByteSource)}.
	 * {@link IOException}s are thrown as {@link UncheckedExecutionException}s.
	 */
	public static Function<MemoryByteSource, MaybeCompressed<MemoryByteSource>> maybeGzip(
			final MemoryByteSourceLoader loader) {
		checkLoader(loader);
		return new Transformer<MemoryByteSource, MaybeCompressed<MemoryByteSource>>() {
			@Override
			MaybeCompressed<MemoryByteSource> transform(MemoryByteSource input) throws IOException {
				return maybeGzip(loader, input);
			}
		};
	}

	/**
	 * Returns a function performing GZIP compression. {@link IOException}s are thrown as
	 * {@link UncheckedExecutionException}s.
	 */
	public static Function<ByteSource, MemoryByteSource> gzip(MemoryByteSourceLoader loader) {
		checkLoader(loader);
		final MemoryByteSourceLoader transformer = loader.transformer(COMPRESS);
		return new Transformer<ByteSource, MemoryByteSource>() {
			@Override
			MemoryByteSource transform(ByteSource input) throws IOException {
				return transformer.load(input);
			}
		};
	}

	/**
	 * Returns a function performing GZIP decompression. {@link IOException}s are thrown as
	 * {@link UncheckedExecutionException}s.
	 */
	public static Function<ByteSource, MemoryByteSource> gunzip(MemoryByteSourceLoader loader) {
		checkLoader(loader);
		final MemoryByteSourceLoader transformer = loader.transformer(DECOMPRESS);
		return new Transformer<ByteSource, MemoryByteSource>() {
			@Override
			MemoryByteSource transform(ByteSource input) throws IOException {
				return transformer.load(input);
			}
		};
	}

	/** Transformer function. */
	static abstract class Transformer<F extends ByteSource, T> implements Function<F, T> {
		Transformer() {
		}

		@Override
		public T apply(F input) {
			checkInput(input);
			try {
				return transform(input);
			} catch (IOException e) {
				throw new UncheckedExecutionException(e);
			}
		}

		abstract T transform(F input) throws IOException;
	}

}
