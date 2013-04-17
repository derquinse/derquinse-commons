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
					ByteBuffer local = ByteBuffer.allocate(1);
					if (channel.read(local) >= 0) {
						throw new IllegalArgumentException(String.format("Maximum size of %d exceeded", maxSize));
					}
					return chunks;
				}
			} else if (loaded < 0) {
				return chunks;
			}
		}
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
	public final MemoryByteSource toDirect(boolean merge) {
		return merge ? merge() : this;
	}
}
