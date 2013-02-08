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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Gson object adapter support.
 * @author Andres Rodriguez
 */
public abstract class GsonObjectAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
	/** Constructor. */
	public GsonObjectAdapter() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object, java.lang.reflect.Type,
	 * com.google.gson.JsonSerializationContext)
	 */
	@Override
	public final JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
		final GsonObjectWriter<T> writer = new GsonObjectWriter<T>(src, typeOfSrc, context);
		serialize(writer);
		return writer.getObject();
	}

	/** Serializes the object. */
	protected abstract void serialize(GsonObjectWriter<T> writer);

	@Override
	public final T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		checkArgument(json.isJsonObject(), "Expected JSON Object to deserialize [%s].", typeOfT);
		final GsonObjectReader<T> reader = new GsonObjectReader<T>(json.getAsJsonObject(), typeOfT, context);
		return deserialize(reader);
	}

	/** Deserializes an object. */
	protected abstract T deserialize(GsonObjectReader<T> reader);
}
