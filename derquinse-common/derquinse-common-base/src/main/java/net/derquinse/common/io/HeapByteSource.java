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

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Base class for byte sources that are be stored in the heap. The byte source may be contiguous or
 * backed a list of chunks.
 * @author Andres Rodriguez
 */
abstract class HeapByteSource extends MemoryByteSource {

	static MemoryByteSource load(InputStream is, int maxSize, int chunkSize) throws IOException {
		final List<ByteArrayByteSource> chunks = Lists.newLinkedList();
		int acc = 0;
		boolean done = false;
		while (!done) {
			// New chunk size
			int ncs = Math.min(chunkSize, maxSize - acc);
			// Load the the new chunk
			final byte[] buffer = new byte[ncs];
			final byte[] bytes;
			final int loaded = is.read(buffer);
			if (loaded > 0) {
				if (loaded < ncs) {
					bytes = Arrays.copyOf(buffer, loaded);
				} else {
					bytes = buffer;
				}
				final ByteArrayByteSource source = new ByteArrayByteSource(bytes);
				acc += loaded;
				checkState(acc <= maxSize, "More bytes loaded than allowed");
				if (loaded < ncs) {
					// last chunck
					done = true;
				} else if (acc == maxSize) {
					if (is.read() != -1) {
						throw new IllegalArgumentException(String.format("Maximum size of %d exceeded", maxSize));
					}
					done = true;
				}
				chunks.add(source);
			}
		}
		final int n = chunks.size();
		if (n == 0) {
			return EmptyByteSource.HEAP;
		} else if (n == 1) {
			return chunks.get(0);
		} else {
			return new ChunkedHeapByteSource(new Chunks<ByteArrayByteSource>(chunks));
		}
	}

	@Override
	public final boolean isHeap() {
		return true;
	}

	@Override
	public final boolean isDirect() {
		return false;
	}

	@Override
	public final MemoryByteSource toHeap(boolean merge) {
		return merge ? merge() : this;
	}
}
