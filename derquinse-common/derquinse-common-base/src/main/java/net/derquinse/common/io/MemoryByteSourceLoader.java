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
import static com.google.common.base.Preconditions.checkState;
import static net.derquinse.common.io.InternalPreconditions.checkChunkSize;
import static net.derquinse.common.io.InternalPreconditions.checkMaxSize;
import static net.derquinse.common.io.InternalPreconditions.checkSize;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;

import com.google.common.base.Objects;
import com.google.common.io.ByteSource;
import com.google.common.io.Closer;
import com.google.common.io.InputSupplier;
import com.google.common.primitives.Ints;

/**
 * Memory byte source loader.
 * @author Andres Rodriguez
 */
@ThreadSafe
public final class MemoryByteSourceLoader {
	/** Whether to use direct memory. */
	private final boolean direct;
	/** Maximum size. */
	private final int maxSize;
	/** Chunk size. */
	private final int chunkSize;
	/** Whether to merge after loading. */
	private final boolean merge;

	/** Creates a new builder. */
	public static Builder builder() {
		return new Builder();
	}

	/** Constructor. */
	private MemoryByteSourceLoader(Builder builder) {
		this.direct = builder.direct;
		this.maxSize = builder.maxSize;
		this.chunkSize = builder.chunkSize;
		this.merge = builder.merge;
	}

	private MemoryByteSource merged(MemoryByteSource source) {
		if (merge) {
			return source.merge();
		}
		return source;
	}

	/**
	 * Loads the contents of the input stream into a memory byte source.
	 * @param is Input stream. It is not closed.
	 * @return The loaded data in a byte source.
	 */
	public MemoryByteSource load(InputStream is) throws IOException {
		checkNotNull(is, "The input stream to load must be provided");
		final MemoryByteSource source;
		if (direct) {
			source = DirectByteSource.load(is, maxSize, chunkSize);
		} else {
			source = HeapByteSource.load(is, maxSize, chunkSize);
		}
		return merged(source);
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
		/*
		if (supplier instanceof MemoryByteSource) {
			return transform((MemoryByteSource) supplier); // for guava 15
		}
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
		return Objects.hashCode(direct, maxSize, chunkSize, merge);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MemoryByteSourceLoader) {
			MemoryByteSourceLoader s = (MemoryByteSourceLoader) obj;
			return direct == s.direct && merge == s.merge && maxSize == s.maxSize && chunkSize == s.chunkSize;
		}
		return false;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("direct", direct).add("maxSize", maxSize).add("chunkSize", chunkSize)
				.add("merge", merge).toString();
	}

	/** Memory byte source loader builder. */
	@NotThreadSafe
	public static final class Builder implements net.derquinse.common.base.Builder<MemoryByteSourceLoader> {
		/** Whether to use direct memory. */
		private boolean direct = false;
		/** Whether the use of direct memory has been set. */
		private boolean directSet = false;
		/** Maximum size. */
		private int maxSize = Integer.MAX_VALUE;
		/** Whether the max size has been set. */
		private boolean maxSizeSet = false;
		/** Chunk size. */
		private int chunkSize = 8192;
		/** Whether the chunk size has been set. */
		private boolean chunkSizeSet = false;
		/** Whether to merge after loading. */
		private boolean merge = false;
		/** Whether the merge flag has been set. */
		private boolean mergeSet = false;

		/** Constructor. */
		private Builder() {
		}

		/**
		 * Specifies if the loader should use direct memory (the default is to use heap memory).
		 * @param direct True to use direct memory, false to use heap.
		 * @return This loader.
		 * @throws IllegalStateException if the use of direct or heap memory has already been set.
		 */
		public Builder setDirect(boolean direct) {
			checkState(!directSet, "The use of direct or heap memory has already been set.");
			this.direct = direct;
			this.directSet = true;
			return this;
		}

		/**
		 * Specifies the maximum size allowed (the default is limited only by memory available).
		 * @return This loader.
		 * @throws IllegalStateException if the maximum size has already been set.
		 * @throws IllegalArgumentException if the argument is not greater than zero.
		 */
		public Builder maxSize(int maxSize) {
			checkState(!maxSizeSet, "The maximum size has already been set");
			this.maxSize = checkMaxSize(maxSize);
			this.maxSizeSet = true;
			return this;
		}

		/**
		 * Specifies the chunk size to use (the default is 8192 bytes).
		 * @return This loader.
		 * @throws IllegalStateException if the chunk size has already been set.
		 * @throws IllegalArgumentException if the argument is not greater than zero.
		 */
		public Builder chunkSize(int chunkSize) {
			checkState(!chunkSizeSet, "The chunk size has already been set");
			this.chunkSize = checkChunkSize(chunkSize);
			this.chunkSizeSet = true;
			return this;
		}

		/**
		 * Specifies if the loader should merge the source after loading (the default is not to do it).
		 * @param merge Whether to merge after loading.
		 * @return This loader.
		 * @throws IllegalStateException if the merge flag has already been set.
		 */
		public Builder setMerge(boolean merge) {
			checkState(!mergeSet, "The merge flag has already been set.");
			this.merge = merge;
			this.mergeSet = true;
			return this;
		}

		/** Builds the loader. */
		@Override
		public MemoryByteSourceLoader build() {
			return new MemoryByteSourceLoader(this);
		}
	}

}