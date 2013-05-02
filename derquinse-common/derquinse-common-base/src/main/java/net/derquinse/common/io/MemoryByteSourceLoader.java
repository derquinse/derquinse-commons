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

import static com.google.common.base.Preconditions.checkNotNull;
import static net.derquinse.common.io.InternalPreconditions.checkChunkSize;
import static net.derquinse.common.io.InternalPreconditions.checkMaxSize;
import static net.derquinse.common.io.InternalPreconditions.checkSize;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.concurrent.ThreadSafe;

import com.google.common.base.Objects;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closer;
import com.google.common.io.InputSupplier;
import com.google.common.primitives.Ints;

/**
 * Memory byte source loader.
 * @author Andres Rodriguez
 */
@ThreadSafe
public final class MemoryByteSourceLoader {
	/** Default loader. */
	private static final MemoryByteSourceLoader DEFAULT = new MemoryByteSourceLoader(false, Integer.MAX_VALUE, 8192,
			false, null);
	/** Whether to use direct memory. */
	private final boolean direct;
	/** Maximum size. */
	private final int maxSize;
	/** Chunk size. */
	private final int chunkSize;
	/** Whether to merge after loading. */
	private final boolean merge;
	/** Transformer to use. */
	private final BytesTransformer transformer;

	/** Gets the default loader. */
	public static MemoryByteSourceLoader get() {
		return DEFAULT;
	}

	/** Constructor. */
	private MemoryByteSourceLoader(boolean direct, int maxSize, int chunkSize, boolean merge, BytesTransformer transformer) {
		this.direct = direct;
		this.maxSize = maxSize;
		this.chunkSize = chunkSize;
		this.merge = merge;
		this.transformer = transformer;
	}

	/** Returns whether the loader uses direct memory. */
	public boolean isDirect() {
		return direct;
	}

	/** Returns the maximum size. */
	public int getMaxSize() {
		return maxSize;
	}

	/** Returns the chunk size. */
	public int getChunkSize() {
		return chunkSize;
	}

	/** Returns whether the loader merges after loading. */
	public boolean isMerge() {
		return merge;
	}

	/** Returns the transformer used. */
	public BytesTransformer getTransformer() {
		return transformer;
	}

	/**
	 * Returns a loader with the same configuration and the use of direct memory specified by the
	 * argument.
	 * @param direct True to use direct memory, false to use heap.
	 */
	public MemoryByteSourceLoader direct(boolean direct) {
		if (direct == this.direct) {
			return this;
		}
		return new MemoryByteSourceLoader(direct, maxSize, chunkSize, merge, transformer);
	}

	/**
	 * Returns a loader with the same configuration and the maximum allowed size specified by the
	 * argument.
	 */
	public MemoryByteSourceLoader maxSize(int maxSize) {
		checkMaxSize(maxSize);
		if (maxSize == this.maxSize) {
			return this;
		}
		return new MemoryByteSourceLoader(direct, maxSize, chunkSize, merge, transformer);
	}

	/**
	 * Returns a loader with the same configuration and the chunk size specified by the argument.
	 */
	public MemoryByteSourceLoader chunkSize(int chunkSize) {
		checkChunkSize(chunkSize);
		if (chunkSize == this.chunkSize) {
			return this;
		}
		return new MemoryByteSourceLoader(direct, maxSize, chunkSize, merge, transformer);
	}

	/**
	 * Returns a loader with the same configuration and that merges the source after loading according
	 * to the provided argument.
	 * @param merge Whether to merge after loading.
	 */
	public MemoryByteSourceLoader merge(boolean merge) {
		if (merge == this.merge) {
			return this;
		}
		return new MemoryByteSourceLoader(direct, maxSize, chunkSize, merge, transformer);
	}

	/**
	 * Returns a loader with the same configuration and the transformer specified by the argument.
	 */
	public MemoryByteSourceLoader transformer(ByteStreamTransformer transformer) {
		BytesTransformer v = transformer == null ? null : BytesTransformer.of(transformer);
		if (Objects.equal(this.transformer, v)) {
			return this;
		}
		return new MemoryByteSourceLoader(direct, maxSize, chunkSize, merge, v);
	}

	private MemoryByteSource merged(MemoryByteSource source) {
		if (merge) {
			return source.merge();
		}
		return source;
	}

	/** Opens a new memory output stream. */
	public MemoryOutputStream openStream() {
		if (direct) {
			return DirectByteSource.openStream(this);
		}
		return HeapByteSource.openStream(this);
	}

	/**
	 * Loads the contents of the input stream into a memory byte source.
	 * @param is Input stream. It is not closed.
	 * @return The loaded data in a byte source.
	 */
	public MemoryByteSource load(InputStream is) throws IOException {
		checkNotNull(is, "The input stream to load must be provided");
		final MemoryOutputStream os = openStream();
		if (transformer != null) {
			transformer.transform(is, os);
		} else {
			ByteStreams.copy(is, os);
		}
		return os.toByteSource();
	}

	/** Performs a copy of the provided source. */
	MemoryByteSource copy(MemoryByteSource source) {
		try {
			return load(source.openStream());
		} catch (IOException e) {
			throw new IllegalStateException(e); // should not happen
		}
	}

	/**
	 * Transform an existing memory byte source.
	 */
	private MemoryByteSource transform(MemoryByteSource source) {
		checkSize(Ints.saturatedCast(source.size()), maxSize);
		final MemoryByteSource transformed;
		if (direct) {
			transformed = source.toDirect(chunkSize);
		} else {
			transformed = source.toHeap(chunkSize);
		}
		return merged(transformed);
	}

	/**
	 * Loads the contents of an existing source into a memory byte source.
	 * @return The loaded data in a byte source.
	 */
	public MemoryByteSource load(ByteSource source) throws IOException {
		checkNotNull(source, "The byte source to load must be provided");
		if (transformer != null) {
			MemoryByteSink sink = newSink();
			transformer.transform(source, sink);
			return sink.queue().remove();
		}
		if (source instanceof MemoryByteSource) {
			return transform((MemoryByteSource) source);
		}
		Closer closer = Closer.create();
		try {
			InputStream is = closer.register(source.openStream());
			return load(is);
		} finally {
			closer.close();
		}
	}

	/**
	 * Loads the contents of an existing input supplier into a memory byte source.
	 * @return The loaded data in a byte source.
	 */
	public MemoryByteSource load(InputSupplier<? extends InputStream> supplier) throws IOException {
		checkNotNull(supplier, "The input supplier to load must be provided");
		if (transformer != null) {
			MemoryByteSink sink = newSink();
			transformer.transform(supplier, sink);
			return sink.queue().remove();
		}
		/*
		 * if (supplier instanceof MemoryByteSource) { return transform((MemoryByteSource) supplier); //
		 * for guava 15 }
		 */
		Closer closer = Closer.create();
		try {
			InputStream is = closer.register(supplier.getInput());
			return load(is);
		} finally {
			closer.close();
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(direct, maxSize, chunkSize, merge, transformer);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MemoryByteSourceLoader) {
			MemoryByteSourceLoader s = (MemoryByteSourceLoader) obj;
			return direct == s.direct && merge == s.merge && maxSize == s.maxSize && chunkSize == s.chunkSize
					&& transformer.equals(s.transformer);
		}
		return false;
	}

	/** Creates a new sink based on this loader. */
	public MemoryByteSink newSink() {
		return new MemoryByteSink(this);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).omitNullValues().add("direct", direct).add("maxSize", maxSize)
				.add("chunkSize", chunkSize).add("merge", merge).add("transformer", transformer).toString();
	}

}