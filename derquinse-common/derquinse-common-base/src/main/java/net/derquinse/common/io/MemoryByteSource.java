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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.io.ByteSource;

/**
 * Base class for byte sources that are guaranteed to be stored in memory, either heap or direct
 * buffers. The byte source may be contiguous or backed a list of chunks. Total size must fit in an
 * integer. The default chunk size is 8 KB.
 * @author Andres Rodriguez
 */
@Beta
public abstract class MemoryByteSource extends ByteSource {
	/** Creates a new loader. */
	public static Loader loader() {
		return new Loader();
	}

	/** Returns whether the data is stored in the heap. */
	public abstract boolean isHeap();

	/** Returns whether the data is stored in direct memory. */
	public abstract boolean isDirect();

	/** Returns the exact number of bytes in the source. */
	@Override
	public abstract long size();

	/**
	 * Merges to a byte source consisting of a single chunk.
	 * @return The merged byte source. Maybe the same one if already consisted of only one chunck.
	 */
	public abstract MemoryByteSource merge();

	/**
	 * Transforms this source into a byte source stored in the heap.
	 * @param merge Whether the result should be merged.
	 * @return The resulting byte source.
	 */
	public abstract MemoryByteSource toHeap(boolean merge);

	/**
	 * Transforms this source into a byte source stored in direct memory.
	 * @param merge Whether the result should be merged.
	 * @return The resulting byte source.
	 */
	public abstract MemoryByteSource toDirect(boolean merge);

	/** Writes to a byte buffer, returning the number of bytes written. */
	abstract int writeTo(ByteBuffer buffer);

	/** Writes to a byte array, at a specified offset, returning the number of bytes written. */
	abstract int writeTo(byte[] buffer, int offset);
	
	/** Returns the number of chunks. */
	abstract int chunks();

	/**
	 * Memory byte source loaders.
	 * @author Andres Rodriguez
	 */
	public static final class Loader {
		/** Whether to use direct memory. */
		private Boolean direct = null;
		/** Maximum size. */
		private Integer maxSize = null;
		/** Chunk size. */
		private int chunkSize = 8192;
		/** Whether the chunk size has been set. */
		private boolean chunkSizeSet = false;

		/** Constructor. */
		private Loader() {
		}

		/**
		 * Specifies if the loader should use direct memory (the default is to use heap memory).
		 * @param direct True to use direct memory, false to use heap.
		 * @return This loader.
		 * @throws IllegalStateException if the use of direct or heap memory has already been set.
		 */
		public Loader setDirect(boolean direct) {
			checkState(this.direct == null, "The use of direct or heap memory has already been set.");
			this.direct = direct;
			return this;
		}

		/**
		 * Specifies the maximum size allowed (the default is limited only by memory available).
		 * @return This loader.
		 * @throws IllegalStateException if the maximum size has already been set.
		 * @throws IllegalArgumentException if the argument is not greater than zero.
		 */
		public Loader maxSize(int maxSize) {
			checkState(this.maxSize == null, "The maxSize method has already been called");
			checkArgument(maxSize >= 0, "The maximum size must be >= 0");
			this.maxSize = maxSize;
			return this;
		}

		/**
		 * Specifies the chunk size to use (the default is 8192 bytes).
		 * @return This loader.
		 * @throws IllegalStateException if the chunk size has already been set.
		 * @throws IllegalArgumentException if the argument is not greater than zero.
		 */
		public Loader chunkSize(int chunkSize) {
			checkState(!chunkSizeSet, "The chunk size has already been set");
			checkArgument(chunkSize >= 0, "The chunk size must be >= 0");
			this.chunkSize = chunkSize;
			this.chunkSizeSet = true;
			return this;
		}

		/**
		 * Loads the contents of the input stream into a memory byte source.
		 * @param is Input stream. It is not closed.
		 * @return The loaded data in a byte source.
		 */
		public MemoryByteSource load(InputStream is) throws IOException {
			final int ms = Objects.firstNonNull(this.maxSize, Integer.MAX_VALUE);
			final int cs = Math.min(ms, Objects.firstNonNull(this.chunkSize, Integer.MAX_VALUE));
			if (Boolean.TRUE.equals(direct)) {
				return DirectByteSource.load(is, ms, cs);
			}
			return HeapByteSource.load(is, ms, cs);
		}

	}

}
