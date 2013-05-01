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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Base class for byte sources that are be stored in the heap. The byte source may be contiguous or
 * backed a list of chunks.
 * @author Andres Rodriguez
 */
abstract class HeapByteSource extends MemoryByteSource {

	private static MemoryByteSource build(List<ByteArrayByteSource> chunks) {
		final int n = chunks.size();
		if (n == 0) {
			return EmptyByteSource.HEAP;
		} else if (n == 1) {
			return chunks.get(0);
		} else {
			return new ChunkedHeapByteSource(new Chunks<ByteArrayByteSource>(chunks));
		}
	}

	static MemoryOutputStream openStream(MemoryByteSourceLoader loader) {
		return new Output(loader);
	}

	/** Constructor. */
	HeapByteSource() {
	}

	@Override
	public final boolean isHeap() {
		return true;
	}

	@Override
	public final boolean isDirect() {
		return false;
	}

	@Override
	public final MemoryByteSource toHeap(boolean merge) {
		return merge ? merge() : this;
	}

	@Override
	public final MemoryByteSource toHeap(int chunkSize) {
		return merge(chunkSize);
	}

	/** Heap-based sink output stream. */
	private static final class Output extends MemoryOutputStream {
		/** Chunks. */
		private final List<ByteArrayByteSource> chunks = Lists.newLinkedList();
		/** Buffer. */
		private byte[] buffer = null;
		/** Current position. */
		private int position = 0;

		/** Constructor. */
		Output(MemoryByteSourceLoader loader) {
			super(loader);
		}

		@Override
		void add(byte b) throws IOException {
			final int chunkSize = loader.getChunkSize();
			if (buffer == null) {
				buffer = new byte[chunkSize];
				position = 0;
			} else if (position >= buffer.length) {
				chunks.add(new ByteArrayByteSource(buffer));
				buffer = new byte[chunkSize];
				position = 0;
			}
			buffer[position] = b;
			position++;
		}

		@Override
		MemoryByteSource build() {
			if (position > 0) {
				final byte[] loaded;
				if (buffer.length - position <= 1) {
					loaded = buffer;
				} else {
					loaded = Arrays.copyOf(buffer, position);
				}
				chunks.add(new ByteArrayByteSource(loaded));
			}
			return HeapByteSource.build(chunks);
		}
	}

}
