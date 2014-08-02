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

import static net.derquinse.common.io.InternalPreconditions.checkChunkSize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Heap for byte source backed by a single byte buffer.
 * @author Andres Rodriguez
 */
final class SingleDirectByteSource extends DirectByteSource {
	/** Backing source. */
	private final BufferByteSource bytes;

	/**
	 * Constructor. The buffer parameters are not modified and its contents should not be modified.
	 * @param bytes Backing buffer.
	 */
	SingleDirectByteSource(ByteBuffer bytes) {
		this.bytes = new BufferByteSource(bytes);
	}

	@Override
	public InputStream openStream() throws IOException {
		return bytes.openStream();
	}

	@Override
	public long size() {
		return bytes.size();
	}

	@Override
	public byte[] read() {
		return bytes.read();
	}

	@Override
	public long copyTo(OutputStream output) throws IOException {
		return bytes.copyTo(output);
	}

	@Override
	public MemoryByteSource merge() {
		return this;
	}

	@Override
	public MemoryByteSource merge(int chunkSize) {
		checkChunkSize(chunkSize);
		return this;
	}

	@Override
	public ByteArrayByteSource toHeap(boolean merge) {
		return new ByteArrayByteSource(read());
	}

	@Override
	public MemoryByteSource toHeap(int chunkSize) {
		checkChunkSize(chunkSize);
		return toHeap(true);
	}

	@Override
	int writeTo(byte[] buffer, int offset) {
		final ByteBuffer b = bytes.view();
		final int sourceLength = b.remaining();
		final int targetLength = buffer.length - offset;
		if (sourceLength <= targetLength) {
			b.get(buffer, offset, sourceLength);
			return sourceLength;
		}
		for (int i = 0; i < targetLength; i++) {
			buffer[offset + i] = b.get();
		}
		return targetLength;
	}

	@Override
	int writeTo(ByteBuffer buffer) {
		ByteBuffer source = bytes.view();
		final int sourceLength = source.remaining();
		final int targetLength = buffer.remaining();
		if (sourceLength <= targetLength) {
			buffer.put(source);
			return sourceLength;
		}
		for (int i = 0; i < targetLength; i++) {
			buffer.put(source.get());
		}
		return targetLength;
	}

	@Override
	int chunks() {
		return 1;
	}

}
