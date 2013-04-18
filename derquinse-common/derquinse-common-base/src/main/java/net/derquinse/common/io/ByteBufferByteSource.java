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
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

/**
 * Heap for byte source backed by a single byte buffer.
 * @author Andres Rodriguez
 */
final class ByteBufferByteSource extends DirectByteSource {
	/** Backing buffer. */
	private final ByteBuffer bytes;

	/**
	 * Constructor. The buffer parameters are not modified and its contents should not be modified.
	 * @param bytes Backing buffer.
	 */
	ByteBufferByteSource(ByteBuffer bytes) {
		this.bytes = checkNotNull(bytes);
	}

	/** Returns a read-only view of the buffer. */
	ByteBuffer view() {
		return bytes.asReadOnlyBuffer();
	}

	@Override
	public InputStream openStream() throws IOException {
		return new ByteBufferInputStream(view());
	}

	@Override
	public long size() {
		return bytes.remaining();
	}

	@Override
	public byte[] read() {
		ByteBuffer b = view();
		byte[] data = new byte[b.remaining()];
		b.get(data);
		return data;
	}

	@Override
	public long copyTo(OutputStream output) throws IOException {
		final WritableByteChannel channel = Channels.newChannel(output);
		final ByteBuffer b = view();
		while (b.remaining() > 0) {
			channel.write(b);
		}
		return size();
	}

	@Override
	public MemoryByteSource merge() {
		return this;
	}

	@Override
	public ByteArrayByteSource toHeap(boolean merge) {
		return new ByteArrayByteSource(read());
	}

	@Override
	int writeTo(byte[] buffer, int offset) {
		final ByteBuffer b = view();
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
		ByteBuffer source = view();
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
