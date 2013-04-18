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

import java.io.InputStream;
import java.nio.ByteBuffer;

import net.derquinse.common.base.NotInstantiable;

import com.google.common.base.Function;
import com.google.common.io.InputSupplier;

/**
 * Provides utility methods for working with byte arrays, byte buffers and I/O streams backed by
 * those.
 * <p>
 * All method parameters must be non-null unless documented otherwise.
 * @author Andres Rodriguez
 */
public final class MemoryByteStreams extends NotInstantiable {
	/** Not instantiable. */
	private MemoryByteStreams() {
	}

	/**
	 * Returns a factory that will supply instances of {@link InputStream} that read from an sliced
	 * view of the given byte buffer.
	 * @param b The input buffer.
	 * @return The requested factory.
	 */
	public static InputSupplier<InputStream> newInputStreamSupplier(ByteBuffer b) {
		return new ByteBufferInputStreamSupplier(b);
	}

	/** Returns a memory byte source transformer that merges the input. */
	public static Function<MemoryByteSource, MemoryByteSource> merge() {
		return MemoryByteSourceTransformers.MERGE;
	}

	/** Returns a memory byte source transformer that moves the input to the heap. */
	public static Function<MemoryByteSource, MemoryByteSource> toHeap(boolean merge) {
		return merge ? MemoryByteSourceTransformers.HEAP_MERGE : MemoryByteSourceTransformers.HEAP;
	}

	/** Returns a memory byte source transformer that moves the input to direct memory. */
	public static Function<MemoryByteSource, MemoryByteSource> toDirect(boolean merge) {
		return merge ? MemoryByteSourceTransformers.DIRECT_MERGE : MemoryByteSourceTransformers.DIRECT;
	}

	/** Memory byte source transformers. */
	private enum MemoryByteSourceTransformers implements Function<MemoryByteSource, MemoryByteSource> {
		MERGE {
			@Override
			public MemoryByteSource apply(MemoryByteSource input) {
				return input.merge();
			}
		},
		HEAP {
			@Override
			public MemoryByteSource apply(MemoryByteSource input) {
				return input.toHeap(false);
			}
		},
		HEAP_MERGE {
			@Override
			public MemoryByteSource apply(MemoryByteSource input) {
				return input.toHeap(true);
			}
		},
		DIRECT {
			@Override
			public MemoryByteSource apply(MemoryByteSource input) {
				return input.toDirect(false);
			}
		},
		DIRECT_MERGE {
			@Override
			public MemoryByteSource apply(MemoryByteSource input) {
				return input.toDirect(true);
			}
		},
	}
}
