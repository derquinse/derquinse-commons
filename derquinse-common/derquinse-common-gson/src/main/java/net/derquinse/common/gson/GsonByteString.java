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
package net.derquinse.common.gson;

import java.io.IOException;

import net.derquinse.common.base.ByteString;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Gson representation for {@link ByteString}.
 * @author Andres Rodriguez
 */
public final class GsonByteString extends TypeAdapter<ByteString> {
	/** Constructor. */
	public GsonByteString() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.gson.TypeAdapter#read(com.google.gson.stream.JsonReader)
	 */
	@Override
	public ByteString read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		final String string = in.nextString();
		return ByteString.fromHexString(string);
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.gson.TypeAdapter#write(com.google.gson.stream.JsonWriter, java.lang.Object)
	 */
	@Override
	public void write(JsonWriter out, ByteString value) throws IOException {
		if (value == null) {
			out.nullValue();
			return;
		}
		out.value(value.toHexString());
	}
}
