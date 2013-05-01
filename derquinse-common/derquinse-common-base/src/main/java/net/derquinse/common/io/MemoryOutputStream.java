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
import java.io.OutputStream;

/**
 * Output stream that stores written bytes in memory readable using a {@link MemoryByteSource}.
 * @author Andres Rodriguez
 */
public abstract class MemoryOutputStream extends OutputStream {
	/** Memory loader. */
	final MemoryByteSourceLoader loader;
	/** Whether the stream is closed. */
	private boolean closed = false;
	/** Result. */
	private volatile MemoryByteSource source;
	/** Total number of bytes written. */
	private int count = 0;

	/** Constructor. */
	MemoryOutputStream(MemoryByteSourceLoader loader) {
		this.loader = loader;
	}

	private void ensureOpen() throws IOException {
		if (closed) {
			throw new IOException("Stream already closed");
		}
	}

	@Override
	public synchronized final void write(int b) throws IOException {
		ensureOpen();
		write((byte) b);
	}

	@Override
	public synchronized final void write(byte[] b, int off, int len) throws IOException {
		ensureOpen();
		if (b == null) {
			throw new NullPointerException();
		} else if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
			throw new IndexOutOfBoundsException();
		} else if (len == 0) {
			return;
		}
		for (int i = 0; i < len; i++) {
			write(b[off + i]);
		}
	}
	
	private void write(byte b) throws IOException {
		final int maxSize = loader.getMaxSize();
		if (count >= maxSize) {
			throw new MaximumSizeExceededException(maxSize);
		}
		add(b);
		count++;
	}

	/**
	 * Closes the stream. One the stream is closed the byte source is available. Closing an
	 * already-closed stream is a no-op.
	 */
	@Override
	public synchronized final void close() {
		if (closed) {
			return;
		}
		if (count == 0) {
			source = loader.isDirect() ? EmptyByteSource.DIRECT : EmptyByteSource.HEAP;
		} else {
			source = build();
			if (loader.isMerge()) {
				source = source.merge();
			}
		}
		closed = true;
	}

	/**
	 * Returns the result. If the stream is open it will be closed.
	 */
	public final MemoryByteSource toByteSource() {
		if (source == null) {
			close();
		}
		return source;
	}

	abstract void add(byte b) throws IOException;

	abstract MemoryByteSource build();
}
