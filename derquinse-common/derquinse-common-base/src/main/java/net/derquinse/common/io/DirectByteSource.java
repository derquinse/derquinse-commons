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
import java.nio.ByteBuffer;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Base class for byte sources that are be stored in direct buffers. The byte source may be
 * contiguous or backed a list of chunks.
 * @author Andres Rodriguez
 */
abstract class DirectByteSource extends MemoryByteSource {

	private static MemoryByteSource build(List<SingleDirectByteSource> chunks) {
		final int n = chunks.size();
		if (n == 0) {
			return EmptyByteSource.DIRECT;
		} else if (n == 1) {
			return chunks.get(0);
		} else {
			return new ChunkedDirectByteSource(new Chunks<SingleDirectByteSource>(chunks));
		}
	}

	static MemoryOutputStream openStream(MemoryByteSourceLoader loader) {
		return new Output(loader);
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
	private static final class Output extends MemoryOutputStream {
		/** Chunks. */
		private final List<SingleDirectByteSource> chunks = Lists.newLinkedList();
		/** Buffer. */
		private ByteBuffer buffer = null;

		/** Constructor. */
		Output(MemoryByteSourceLoader loader) {
			super(loader);
		}

		@Override
		void add(byte b) throws IOException {
			final int chunkSize = loader.getChunkSize();
			if (buffer == null) {
				buffer = ByteBuffer.allocateDirect(chunkSize);
			} else if (buffer.remaining() <= 0) {
				buffer.flip();
				chunks.add(new SingleDirectByteSource(buffer));
				buffer = ByteBuffer.allocateDirect(chunkSize);
			}
			buffer.put(b);
		}

		@Override
		MemoryByteSource build() {
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
			chunks.add(new SingleDirectByteSource(bytes));
			return DirectByteSource.build(chunks);
		}
	}

}
