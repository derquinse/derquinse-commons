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
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.google.common.annotations.Beta;
import com.google.common.io.InputSupplier;
import com.google.common.primitives.Ints;

/**
 * An input stream supplier backed by a byte buffer.
 * @author Andres Rodriguez
 */
@Beta
final class ByteBufferInputStreamSupplier implements InputSupplier<InputStream> {
	/** Backing buffer. */
	private final ByteBuffer buffer;

	/**
	 * Constructor.
	 * @param buffer Backing buffer. An sliced view is used as input. The buffer will not be modified
	 *          by this object.
	 */
	ByteBufferInputStreamSupplier(ByteBuffer buffer) {
		this.buffer = checkNotNull(buffer, "The backing buffer must be provided").slice();
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.common.io.InputSupplier#getInput()
	 */
	@Override
	public synchronized InputStream getInput() throws IOException {
		return new ByteBufferInputStream(buffer.duplicate());
	}

	private static final class ByteBufferInputStream extends InputStream {
		/** Backing buffer. */
		private final ByteBuffer buffer;

		/**
		 * Constructor.
		 * @param buffer Backing buffer. The buffer will not be modified by this object.
		 */
		ByteBufferInputStream(ByteBuffer buffer) {
			this.buffer = checkNotNull(buffer, "The backing buffer must be provided");
		}

		@Override
		public synchronized int available() throws IOException {
			return buffer.remaining();
		}

		@Override
		public synchronized int read() throws IOException {
			return buffer.remaining() > 0 ? (buffer.get() & 0xff) : -1;
		}

		@Override
		public synchronized int read(byte[] b, int off, int len) throws IOException {
			if (b == null) {
				throw new NullPointerException();
			} else if (off < 0 || len < 0 || len > b.length - off) {
				throw new IndexOutOfBoundsException();
			} else if (len == 0) {
				return 0;
			}
			final int r = buffer.remaining();
			if (r == 0) {
				return -1; // EOF
			}
			final int n = Math.min(len, r);
			buffer.get(b, off, n);
			return n;
		}

		@Override
		public synchronized long skip(long n) throws IOException {
			if (n <= 0) {
				return 0;
			}
			final int r = buffer.remaining();
			if (r == 0) {
				return 0; // EOF
			}
			final int skipped = Math.min(Ints.saturatedCast(n), r);
			buffer.position(buffer.position() + skipped);
			return skipped;
		}

	}
}
