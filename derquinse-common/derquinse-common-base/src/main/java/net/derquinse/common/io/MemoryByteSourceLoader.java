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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.io.InputStream;

import com.google.common.base.Objects;

/**
 * Memory byte source loader.
 * @author Andres Rodriguez
 */
public final class MemoryByteSourceLoader {
	/** Whether to use direct memory. */
	private Boolean direct = null;
	/** Maximum size. */
	private Integer maxSize = null;
	/** Chunk size. */
	private int chunkSize = 8192;
	/** Whether the chunk size has been set. */
	private boolean chunkSizeSet = false;

	/** Creates a new loader. */
	public static MemoryByteSourceLoader create() {
		return new MemoryByteSourceLoader();
	}

	/** Constructor. */
	private MemoryByteSourceLoader() {
	}

	/**
	 * Specifies if the loader should use direct memory (the default is to use heap memory).
	 * @param direct True to use direct memory, false to use heap.
	 * @return This loader.
	 * @throws IllegalStateException if the use of direct or heap memory has already been set.
	 */
	public MemoryByteSourceLoader setDirect(boolean direct) {
		checkState(this.direct == null, "The use of direct or heap memory has already been set.");
		this.direct = direct;
		return this;
	}

	/**
	 * Specifies the maximum size allowed (the default is limited only by memory available).
	 * @return This loader.
	 * @throws IllegalStateException if the maximum size has already been set.
	 * @throws IllegalArgumentException if the argument is not greater than zero.
	 */
	public MemoryByteSourceLoader maxSize(int maxSize) {
		checkState(this.maxSize == null, "The maxSize method has already been called");
		checkArgument(maxSize >= 0, "The maximum size must be >= 0");
		this.maxSize = maxSize;
		return this;
	}

	/**
	 * Specifies the chunk size to use (the default is 8192 bytes).
	 * @return This loader.
	 * @throws IllegalStateException if the chunk size has already been set.
	 * @throws IllegalArgumentException if the argument is not greater than zero.
	 */
	public MemoryByteSourceLoader chunkSize(int chunkSize) {
		checkState(!chunkSizeSet, "The chunk size has already been set");
		checkArgument(chunkSize >= 0, "The chunk size must be >= 0");
		this.chunkSize = chunkSize;
		this.chunkSizeSet = true;
		return this;
	}

	/**
	 * Loads the contents of the input stream into a memory byte source.
	 * @param is Input stream. It is not closed.
	 * @return The loaded data in a byte source.
	 */
	public MemoryByteSource load(InputStream is) throws IOException {
		final int ms = Objects.firstNonNull(this.maxSize, Integer.MAX_VALUE);
		final int cs = Math.min(ms, Objects.firstNonNull(this.chunkSize, Integer.MAX_VALUE));
		if (Boolean.TRUE.equals(direct)) {
			return DirectByteSource.load(is, ms, cs);
		}
		return HeapByteSource.load(is, ms, cs);
	}

}