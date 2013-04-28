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
import static net.derquinse.common.io.InternalPreconditions.checkSourceArray;
import static net.derquinse.common.io.InternalPreconditions.checkSourceBuffer;

import java.nio.ByteBuffer;

import com.google.common.annotations.Beta;
import com.google.common.io.ByteSource;

/**
 * Base class for byte sources that are guaranteed to be stored in memory, either in byte arrays in
 * the heap or direct buffers. The byte source may be contiguous or backed a list of chunks. Total
 * size must fit in an integer. The default chunk size is 8 KB.
 * @author Andres Rodriguez
 */
@Beta
public abstract class MemoryByteSource extends ByteSource {
	/** Creates a heap memory byte source backed by a copy of the provided array. */
	public static MemoryByteSource heapCopyOf(byte[] source) {
		return new ByteArrayByteSource(checkSourceArray(source).clone());
	}

	/** Creates a direct memory byte source backed by a copy of the provided array. */
	public static MemoryByteSource directCopyOf(byte[] source) {
		final int size = checkSourceArray(source).length;
		ByteBuffer buffer = ByteBuffer.allocateDirect(size);
		buffer.put(source);
		buffer.flip();
		return new ByteBufferByteSource(buffer);
	}

	/** Creates a heap memory byte source that is backed by a the provided array. */
	public static MemoryByteSource wrap(byte[] source) {
		return new ByteArrayByteSource(checkNotNull(source, "The source array must be provided"));
	}

	/** Creates a heap memory byte source backed by a copy of the provided buffer, which is consumed. */
	public static MemoryByteSource heapCopyOf(ByteBuffer source) {
		final int size = checkSourceBuffer(source).remaining();
		final byte[] bytes = new byte[size];
		source.get(bytes);
		return wrap(bytes);
	}

	/**
	 * Creates a direct memory byte source backed by a copy of the provided buffer, which is consumed.
	 */
	public static MemoryByteSource directCopyOf(ByteBuffer source) {
		final int size = checkSourceBuffer(source).remaining();
		ByteBuffer buffer = ByteBuffer.allocateDirect(size);
		buffer.put(source);
		buffer.flip();
		return new ByteBufferByteSource(buffer);
	}

	/** Constructor. */
	MemoryByteSource() {
	}

	/** Returns whether the data is stored in the heap. */
	public abstract boolean isHeap();

	/** Returns whether the data is stored in direct memory. */
	public abstract boolean isDirect();

	/** Returns the exact number of bytes in the source. */
	@Override
	public abstract long size();

	/**
	 * Merges to a byte source consisting of a single chunk.
	 * @return The merged byte source. Maybe the same one if already consisted of only one chunck.
	 */
	public abstract MemoryByteSource merge();

	/**
	 * Merges to a byte source consisting with a chunk size of at least the provided argument. If the
	 * argument is smaller than the size, the byte source will be merged to a single chunk.
	 * @param chunkSize Requested chunk size.
	 * @return The merged byte source. Maybe the same one if already consisted of only one chunck.
	 */
	public abstract MemoryByteSource merge(int chunkSize);

	/**
	 * Transforms this source into a byte source stored in the heap.
	 * @param merge Whether the result should be merged.
	 * @return The resulting byte source.
	 */
	public abstract MemoryByteSource toHeap(boolean merge);

	/**
	 * Transforms this source into a byte source stored in direct memory.
	 * @param merge Whether the result should be merged.
	 * @return The resulting byte source.
	 */
	public abstract MemoryByteSource toDirect(boolean merge);

	/**
	 * Transforms this source into a byte source stored in the heap with at least the specified chunk
	 * size.
	 * @param chunkSize Requested chunk size.
	 * @return The resulting byte source.
	 */
	public abstract MemoryByteSource toHeap(int chunkSize);

	/**
	 * Transforms this source into a byte source stored in direct memory with at least the specified
	 * chunk size.
	 * @param chunkSize Requested chunk size.
	 * @return The resulting byte source.
	 */
	public abstract MemoryByteSource toDirect(int chunkSize);

	/** Writes to a byte buffer, returning the number of bytes written. */
	abstract int writeTo(ByteBuffer buffer);

	/** Writes to a byte array, at a specified offset, returning the number of bytes written. */
	abstract int writeTo(byte[] buffer, int offset);

	/** Returns the number of chunks. */
	abstract int chunks();

}
