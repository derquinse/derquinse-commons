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
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;

/**
 * Empty byte source.
 * @author Andres Rodriguez
 */
final class EmptyByteSource extends MemoryByteSource {
	/** Shared heap instance. */
	static final EmptyByteSource HEAP = new EmptyByteSource(false);
	/** Shared direct instance. */
	static final EmptyByteSource DIRECT = new EmptyByteSource(true);

	/** Whether the source is considered heap or direct. */
	private final boolean direct;

	/**
	 * Constructor.
	 */
	private EmptyByteSource(boolean direct) {
		this.direct = direct;
	}

	@Override
	public boolean isHeap() {
		return !direct;
	}

	@Override
	public boolean isDirect() {
		return direct;
	}

	@Override
	public InputStream openStream() throws IOException {
		return EmptyInputStream.INSTANCE;
	}

	@Override
	public long size() {
		return 0L;
	}

	@Override
	public byte[] read() throws IOException {
		return EmptyInputStream.EMPTY_ARRAY;
	}

	@Override
	public long copyTo(OutputStream output) throws IOException {
		return 0L;
	}

	@Override
	public HashCode hash(HashFunction hashFunction) throws IOException {
		return hashFunction.hashBytes(EmptyInputStream.EMPTY_ARRAY);
	}

	// TODO(user): Possibly override slice()

	@Override
	public MemoryByteSource merge() {
		return this;
	}

	@Override
	public MemoryByteSource toHeap(boolean merge) {
		return HEAP;
	}

	@Override
	public MemoryByteSource toDirect(boolean merge) {
		return DIRECT;
	}

	@Override
	int writeTo(ByteBuffer buffer) {
		return 0;
	}

	@Override
	int writeTo(byte[] buffer, int offset) {
		return 0;
	}

	@Override
	public String toString() {
		return direct ? "EmptyDirectByteSource" : "EmptyHeapByteSource";
	}

}
