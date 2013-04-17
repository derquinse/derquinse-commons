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
 * Direct memory byte source backed by several chunks.
 * @author Andres Rodriguez
 */
final class ChunkedDirectByteSource extends DirectByteSource {
	/** Backing chunks. */
	private final Chunks<ByteBufferByteSource> chunks;

	/**
	 * Constructor. The caller must guarantee that the chunks are of the correct type.
	 * @param chunks Backing chunks.
	 */
	ChunkedDirectByteSource(Chunks<ByteBufferByteSource> chunks) {
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
		final ByteBuffer buffer = ByteBuffer.allocateDirect(chunks.getTotalSize());
		writeTo(buffer);
		return new ByteBufferByteSource(buffer);
	}

	@Override
	public MemoryByteSource toHeap(boolean merge) {
		if (merge) {
			byte[] buffer = new byte[chunks.getTotalSize()];
			writeTo(buffer, 0);
			return new ByteArrayByteSource(buffer);
		} else {
			List<ByteArrayByteSource> list = Lists.newArrayListWithCapacity(chunks.size());
			for (ByteBufferByteSource s : chunks) {
				list.add(s.toHeap(true));
			}
			return new ChunkedHeapByteSource(new Chunks<ByteArrayByteSource>(list));
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

}
