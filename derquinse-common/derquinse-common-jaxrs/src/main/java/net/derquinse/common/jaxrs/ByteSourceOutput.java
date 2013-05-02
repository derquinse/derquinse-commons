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
package net.derquinse.common.jaxrs;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import net.derquinse.common.base.NotInstantiable;
import net.derquinse.common.io.MemoryByteSource;

import com.google.common.io.ByteSource;

/**
 * Utility methods for building JAX-RS outputs based on {@link ByteSource}s.
 * @author Andres Rodriguez.
 */
public final class ByteSourceOutput extends NotInstantiable {
	/** Not instantiable. */
	private ByteSourceOutput() {
	}

	private static <T extends ByteSource> T checkSource(T source) {
		return checkNotNull(source, "The byte source must be provided");
	}

	/** Creates an {@link StreamingOutput} from the byte source. */
	public static StreamingOutput output(final ByteSource source) {
		checkSource(source);
		return new StreamingOutput() {
			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException {
				source.copyTo(output);
			}
		};
	}

	/**
	 * Adds the Content-Length header to a response if the {@link ByteSource} is a
	 * {@link MemoryByteSource}.
	 * @return The provided response builder.
	 */
	public static ResponseBuilder addLength(ResponseBuilder builder, ByteSource source) {
		if (checkSource(source) instanceof MemoryByteSource) {
			builder.header(HttpHeaders.CONTENT_LENGTH, Long.toString(((MemoryByteSource)source).size()));
		}
		return builder;
	}

}
