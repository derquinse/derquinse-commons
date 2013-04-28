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

import static com.google.common.base.Preconditions.checkState;
import static net.derquinse.common.io.InternalPreconditions.checkChunkSize;
import static net.derquinse.common.io.InternalPreconditions.checkMaxSize;
import static net.derquinse.common.io.InternalPreconditions.checkSize;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Base class for byte sources that are be stored in the heap. The byte source may be contiguous or
 * backed a list of chunks.
 * @author Andres Rodriguez
 */
abstract class HeapByteSource extends MemoryByteSource {

	static MemoryByteSource load(InputStream is, int maxSize, int chunkSize) throws IOException {
		final List<ByteArrayByteSource> chunks = Lists.newLinkedList();
		int acc = 0;
		boolean done = false;
		while (!done) {
			// New chunk size
			int ncs = Math.min(chunkSize, maxSize - acc);
			// Load the new chunk
			final byte[] buffer = new byte[ncs];
			final byte[] bytes;
			final int loaded = is.read(buffer);
			if (loaded > 0) {
				if (loaded < ncs) {
					bytes = Arrays.copyOf(buffer, loaded);
				} else {
					bytes = buffer;
				}
				final ByteArrayByteSource source = new ByteArrayByteSource(bytes);
				acc += loaded;
				checkState(acc <= maxSize, "More bytes loaded than allowed");
				if (loaded < ncs) {
					// last chunck
					done = true;
				} else if (acc == maxSize) {
					checkSize(acc + tryReadOne(is), maxSize);
					done = true;
				}
				chunks.add(source);
			} else {
				done = true; // EOF
			}
		}
		return build(chunks);
	}

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

	private static final int tryReadOne(InputStream is) throws IOException {
		return is.read() < 0 ? 0 : 1;
	}

	static SinkOutputStream openStream(MemoryByteSink sink, int maxSize, int chunkSize) {
		return new Output(sink, maxSize, chunkSize);
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
	private static final class Output extends SinkOutputStream {
		/** Chunks. */
		private final List<ByteArrayByteSource> chunks = Lists.newLinkedList();
		/** Max size. */
		private final int maxSize;
		/** Max size. */
		private final int chunkSize;
		/** Buffer. */
		private byte[] buffer = null;
		/** Current position. */
		private int position = 0;
		/** Total number of bytes written. */
		private int count = 0;

		/** Constructor. */
		Output(MemoryByteSink sink, int maxSize, int chunkSize) {
			super(sink);
			this.maxSize = checkMaxSize(maxSize);
			this.chunkSize = checkChunkSize(chunkSize);
		}

		@Override
		void write(byte b) throws IOException {
			if (count >= maxSize) {
				throw new IOException("Stream max size reached");
			}
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
			count++;
		}

		@Override
		MemoryByteSource build() {
			if (count == 0) {
				return EmptyByteSource.HEAP;
			}
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
