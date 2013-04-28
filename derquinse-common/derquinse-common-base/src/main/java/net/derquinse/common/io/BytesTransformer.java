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

import static net.derquinse.common.io.InternalPreconditions.checkInput;
import static net.derquinse.common.io.InternalPreconditions.checkOutput;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.Closer;
import com.google.common.io.InputSupplier;
import com.google.common.io.OutputSupplier;

/**
 * Transformer for byte streams, suppliers and sources.
 * @author Andres Rodriguez
 */
public final class BytesTransformer implements ByteStreamTransformer {
	/** Byte streams transformer to use. */
	private final ByteStreamTransformer transformer;

	/**
	 * Returns a {@link BytesTransformer} for a {@link ByteStreamTransformer}. If the provided object
	 * is already a {@link BytesTransformer} the same object is returned.
	 */
	public static BytesTransformer of(ByteStreamTransformer transformer) {
		if (transformer instanceof BytesTransformer) {
			return (BytesTransformer) transformer;
		} else {
			return new BytesTransformer(transformer);
		}
	}

	/** Constructor. */
	private BytesTransformer(ByteStreamTransformer transformer) {
		this.transformer = Preconditions.checkNotNull(transformer, "The byte stream transformer must be provided");
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.io.ByteStreamTransformer#transform(java.io.InputStream,
	 * java.io.OutputStream)
	 */
	@Override
	public void transform(InputStream input, OutputStream output) throws IOException {
		checkInput(input);
		checkOutput(output);
		transformer.transform(input, output);
	}

	/** Transforms a stream of bytes. */
	public void transform(InputStream input, OutputSupplier<? extends OutputStream> output) throws IOException {
		checkInput(input);
		checkOutput(output);
		final Closer closer = Closer.create();
		try {
			transform(input, closer.register(output.getOutput()));
		} finally {
			closer.close();
		}
	}

	/** Transforms a stream of bytes. */
	public void transform(InputStream input, ByteSink output) throws IOException {
		checkInput(input);
		checkOutput(output);
		final Closer closer = Closer.create();
		try {
			transform(input, closer.register(output.openStream()));
		} finally {
			closer.close();
		}
	}

	/** Transforms a stream of bytes. */
	public void transform(InputSupplier<? extends InputStream> input, OutputSupplier<? extends OutputStream> output)
			throws IOException {
		checkInput(input);
		checkOutput(output);
		final Closer closer = Closer.create();
		try {
			transform(closer.register(input.getInput()), closer.register(output.getOutput()));
		} finally {
			closer.close();
		}
	}

	/** Transforms a stream of bytes. */
	public void transform(InputSupplier<? extends InputStream> input, ByteSink output) throws IOException {
		checkInput(input);
		checkOutput(output);
		final Closer closer = Closer.create();
		try {
			transform(closer.register(input.getInput()), closer.register(output.openStream()));
		} finally {
			closer.close();
		}
	}

	/** Transforms a byte source. */
	public void transform(ByteSource input, ByteSink output) throws IOException {
		checkInput(input);
		checkOutput(output);
		final Closer closer = Closer.create();
		try {
			transform(closer.register(input.openStream()), closer.register(output.openStream()));
		} finally {
			closer.close();
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(BytesTransformer.class, transformer);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BytesTransformer) {
			return transformer.equals(((BytesTransformer) obj).transformer);
		}
		return false;
	}

	@Override
	public String toString() {
		return new StringBuilder("BytesTransformer(").append(transformer).append(')').toString();
	}
}
