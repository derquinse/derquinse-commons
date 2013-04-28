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
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Base class for byte sources that are be stored in direct buffers. The byte source may be
 * contiguous or backed a list of chunks.
 * @author Andres Rodriguez
 */
abstract class DirectByteSource extends MemoryByteSource {

	static MemoryByteSource load(InputStream is, int maxSize, int chunkSize) throws IOException {
		final ReadableByteChannel channel = Channels.newChannel(is);
		final List<ByteBufferByteSource> chunks = load(channel, maxSize, chunkSize);
		return build(chunks);
	}

	private static MemoryByteSource build(List<ByteBufferByteSource> chunks) {
		final int n = chunks.size();
		if (n == 0) {
			return EmptyByteSource.DIRECT;
		} else if (n == 1) {
			return chunks.get(0);
		} else {
			return new ChunkedDirectByteSource(new Chunks<ByteBufferByteSource>(chunks));
		}
	}

	private static List<ByteBufferByteSource> load(ReadableByteChannel channel, int maxSize, int chunkSize)
			throws IOException {
		final List<ByteBufferByteSource> chunks = Lists.newLinkedList();
		int acc = 0;
		while (true) {
			// New chunk size
			int ncs = Math.min(chunkSize, maxSize - acc);
			checkState(ncs > 0);
			// Load the the new chunk
			final ByteBuffer buffer = ByteBuffer.allocateDirect(ncs);
			final int loaded = channel.read(buffer);
			buffer.flip();
			if (loaded > 0) {
				final ByteBuffer bytes;
				if (loaded < ncs) {
					bytes = ByteBuffer.allocateDirect(loaded);
					bytes.put(buffer);
					bytes.flip();
				} else {
					bytes = buffer;
				}
				acc += loaded;
				checkState(acc <= maxSize, "More bytes loaded than allowed");
				final ByteBufferByteSource source = new ByteBufferByteSource(bytes);
				chunks.add(source);
				if (acc == maxSize) {
					checkSize(acc + tryReadOne(channel), maxSize);
					return chunks;
				}
			} else if (loaded < 0) {
				return chunks;
			}
		}
	}

	private static final int tryReadOne(ReadableByteChannel channel) throws IOException {
		ByteBuffer local = ByteBuffer.allocate(1);
		return (channel.read(local) >= 0) ? 1 : 0;
	}

	static SinkOutputStream openStream(MemoryByteSink sink, int maxSize, int chunkSize) {
		return new Output(sink, maxSize, chunkSize);
	}

	/** Constructor. */
	DirectByteSource() {
	}

	@Override
	public final boolean isHeap() {
		return false;
	}

	@Override
	public final boolean isDirect() {
		return true;
	}

	@Override
	public final MemoryByteSource toDirect(boolean merge) {
		return merge ? merge() : this;
	}

	@Override
	public final MemoryByteSource toDirect(int chunkSize) {
		return merge(chunkSize);
	}

	/** Direct buffer-based sink output stream. */
	private static final class Output extends SinkOutputStream {
		/** Chunks. */
		private final List<ByteBufferByteSource> chunks = Lists.newLinkedList();
		/** Max size. */
		private final int maxSize;
		/** Max size. */
		private final int chunkSize;
		/** Buffer. */
		private ByteBuffer buffer = null;
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
				buffer = ByteBuffer.allocateDirect(chunkSize);
			} else if (buffer.remaining() <= 0) {
				buffer.flip();
				chunks.add(new ByteBufferByteSource(buffer));
				buffer = ByteBuffer.allocateDirect(chunkSize);
			}
			buffer.put(b);
			count++;
		}

		@Override
		MemoryByteSource build() {
			if (count == 0) {
				return EmptyByteSource.DIRECT;
			}
			// Whether we have to copy to a smaller buffer
			final boolean copy = buffer.remaining() > 0;
			final int loaded = buffer.position();
			// We're done with the current buffer
			buffer.flip();
			final ByteBuffer bytes;
			if (copy) {
				bytes = ByteBuffer.allocateDirect(loaded);
				bytes.put(buffer);
				bytes.flip();
			} else {
				bytes = buffer;
			}
			chunks.add(new ByteBufferByteSource(bytes));
			return DirectByteSource.build(chunks);
		}
	}

}
