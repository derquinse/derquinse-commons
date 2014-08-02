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

import com.google.common.io.ByteSource;

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
	 * Returns a factory that will supply instances of {@link ByteSource} that read from an sliced
	 * view of the given byte buffer.
	 * @param b The input buffer.
	 * @return The requested factory.
	 */
	public static ByteSource newInputStreamSupplier(ByteBuffer b) {
		return new BufferByteSource(b);
	}

	/** Returns an input stream that reads the remaining bytes of the bprovided buffer. */
	public static InputStream newInputStream(ByteBuffer buffer) {
		return new ByteBufferInputStream(buffer);
	}

}
