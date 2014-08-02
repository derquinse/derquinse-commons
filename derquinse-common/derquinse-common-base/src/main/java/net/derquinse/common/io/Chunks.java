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
import static com.google.common.base.Preconditions.checkNotNull;
import static net.derquinse.common.io.InternalPreconditions.checkChunkSize;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

import com.google.common.collect.ForwardingList;
import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteSource;
import com.google.common.primitives.Ints;

/**
 * A collection of chunks.
 * @author Andres Rodriguez
 */
final class Chunks<T extends MemoryByteSource> extends ForwardingList<T> {
	/** Sources. */
	private final ImmutableList<T> sources;
	/** Backing supplier. */
	private final ByteSource supplier;
	/** Total size. */
	private final int totalSize;
	/** Chunk size. */
	private final int chunkSize;

	/**
	 * Constructor.
	 * @param sources Backing sources, there must be at least two.
	 */
	Chunks(List<? extends T> sources) {
		checkNotNull(sources);
		checkArgument(sources.size() > 1, "There must be at least two chunks");
		this.sources = ImmutableList.copyOf(sources);
		int total = Ints.saturatedCast(this.sources.get(0).size());
		this.chunkSize = total;
		for (int i = 1; i < this.sources.size(); i++) {
			total += Ints.saturatedCast(this.sources.get(i).size());
		}
		this.totalSize = total;
		this.supplier = ByteSource.concat(this.sources);
	}

	protected List<T> delegate() {
		return sources;
	}

	/** Returns the chunk size. */
	int getChunkSize() {
		return chunkSize;
	}

	/** Opens a stream. */
	public InputStream openStream() throws IOException {
		return supplier.openStream();
	}

	/** Returns the total size. */
	int getTotalSize() {
		return totalSize;
	}

	/** Writes to a byte array, at a specified offset, returning the number of bytes written. */
	int writeTo(byte[] buffer, int offset) {
		int written = 0;
		for (T s : sources) {
			int n = s.writeTo(buffer, offset);
			written += n;
			offset += n;
		}
		return written;
	}

	/** Writes to a byte buffer, returning the number of bytes written. */
	int writeTo(ByteBuffer buffer) {
		int written = 0;
		for (T s : sources) {
			int n = s.writeTo(buffer);
			written += n;
		}
		return written;
	}

	/** Merges to a specified chunk size. */
	MemoryByteSource merge(MemoryByteSource container, int chunkSize) {
		checkChunkSize(chunkSize);
		if (chunkSize <= this.chunkSize) {
			return container;
		} else if (chunkSize >= totalSize) {
			return container.merge();
		}
		return MemoryByteSourceLoader.get().chunkSize(chunkSize).direct(container.isDirect()).copy(container);
	}

}
