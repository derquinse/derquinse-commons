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
import java.io.OutputStream;

/**
 * Base class for sink-created ouput streams.
 * @author Andres Rodriguez
 */
abstract class SinkOutputStream extends OutputStream {
	/** Owning sink. */
	private final MemoryByteSink sink;
	/** Whether the stream is closed. */
	private boolean closed = false;

	/** Constructor. */
	SinkOutputStream(MemoryByteSink sink) {
		this.sink = checkNotNull(sink);
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

	@Override
	public synchronized final void close() throws IOException {
		ensureOpen();
		MemoryByteSource source = build();
		sink.add(source);
		closed = true;
	}

	abstract void write(byte b) throws IOException;

	abstract MemoryByteSource build();
}
