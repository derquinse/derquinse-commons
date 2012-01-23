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

import static com.google.common.base.Preconditions.checkArgument;

import java.lang.reflect.Type;

import net.derquinse.common.base.ByteString;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Gson representation for {@link ByteString}.
 * @author Andres Rodriguez
 */
public final class GsonByteString implements JsonSerializer<ByteString>, JsonDeserializer<ByteString> {
	/** Constructor. */
	public GsonByteString() {
	}

	@Override
	public JsonElement serialize(ByteString src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.toHexString());
	}

	@Override
	public ByteString deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		checkArgument(json.isJsonPrimitive(), "Expected JSON String to deserialize ByteString object.");
		final String string = json.getAsString();
		return ByteString.fromHexString(string);
	}
}
