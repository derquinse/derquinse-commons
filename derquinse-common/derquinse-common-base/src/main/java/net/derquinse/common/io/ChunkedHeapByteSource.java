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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Heap for byte source backed by several chunks.
 * @author Andres Rodriguez
 */
final class ChunkedHeapByteSource extends HeapByteSource {
	/** Backing chunks. */
	private final Chunks<ByteArrayByteSource> chunks;

	/**
	 * Constructor. The caller must guarantee that the chunks are of the correct type.
	 * @param chunks Backing chunks.
	 */
	ChunkedHeapByteSource(Chunks<ByteArrayByteSource> chunks) {
		this.chunks = checkNotNull(chunks);
	}

	@Override
	public InputStream openStream() throws IOException {
		return chunks.openStream();
	}

	@Override
	public long size() {
		return chunks.getTotalSize();
	}

	@Override
	public MemoryByteSource merge() {
		final byte[] buffer = new byte[chunks.getTotalSize()];
		writeTo(buffer, 0);
		return new ByteArrayByteSource(buffer);
	}

	@Override
	public MemoryByteSource toDirect(boolean merge) {
		if (merge) {
			ByteBuffer buffer = ByteBuffer.allocateDirect(chunks.getTotalSize());
			writeTo(buffer);
			buffer.flip();
			return new ByteBufferByteSource(buffer);
		} else {
			List<ByteBufferByteSource> list = Lists.newArrayListWithCapacity(chunks.size());
			for (ByteArrayByteSource s : chunks) {
				list.add(s.toDirect(true));
			}
			return new ChunkedDirectByteSource(new Chunks<ByteBufferByteSource>(list));
		}
	}

	@Override
	int writeTo(byte[] buffer, int offset) {
		return chunks.writeTo(buffer, offset);
	}

	@Override
	int writeTo(ByteBuffer buffer) {
		return chunks.writeTo(buffer);
	}

	@Override
	int chunks() {
		return chunks.size();
	}

}
