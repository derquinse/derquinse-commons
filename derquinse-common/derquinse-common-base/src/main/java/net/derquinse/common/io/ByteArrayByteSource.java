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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.io.BaseEncoding;

/**
 * Heap for byte source backed by a single byte array.
 * @author Andres Rodriguez
 */
final class ByteArrayByteSource extends HeapByteSource {
	/** Backing array. */
	private final byte[] bytes;

	/**
	 * Constructor. The array is not copied so it should not be modified.
	 * @param bytes Backing bytes.
	 */
	ByteArrayByteSource(byte[] bytes) {
		this.bytes = checkNotNull(bytes);
	}

	@Override
	public InputStream openStream() throws IOException {
		return new ByteArrayInputStream(bytes);
	}

	@Override
	public long size() {
		return bytes.length;
	}

	@Override
	public byte[] read() {
		return bytes.clone();
	}

	@Override
	public long copyTo(OutputStream output) throws IOException {
		output.write(bytes);
		return bytes.length;
	}

	@Override
	public HashCode hash(HashFunction hashFunction) throws IOException {
		return hashFunction.hashBytes(bytes);
	}

	// TODO(user): Possibly override slice()

	@Override
	public MemoryByteSource merge() {
		return this;
	}

	@Override
	public ByteBufferByteSource toDirect(boolean merge) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
		writeTo(buffer);
		buffer.flip();
		return new ByteBufferByteSource(buffer);
	}

	@Override
	int writeTo(ByteBuffer buffer) {
		final int n = Math.min(bytes.length, buffer.remaining());
		buffer.put(bytes, 0, n);
		return n;
	}

	@Override
	int writeTo(byte[] buffer, int offset) {
		final int n = Math.max(0, Math.min(bytes.length, buffer.length - offset));
		if (n > 0) {
			System.arraycopy(bytes, 0, buffer, offset, n);
		}
		return n;
	}

	@Override
	public String toString() {
		return "ByteArrayByteSource(" + BaseEncoding.base16().encode(bytes) + ")";
	}

}
