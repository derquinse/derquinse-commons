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

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Type;

import javax.annotation.Nullable;

import net.derquinse.common.meta.StringMetaProperty;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Reader for gson object adapter support.
 * @author Andres Rodriguez
 */
public final class GsonObjectReader<T> {
	private static <T> T valueOrDefault(@Nullable T value, @Nullable T defaultValue) {
		if (value != null) return value;
		return defaultValue;
	}

	/** Gson object. */
	private final JsonObject object;
	/** Object type. */
	private final Type type;
	/** Deserialization context. */
	private final JsonDeserializationContext context;
	
	/** Constructor. */
	GsonObjectReader(JsonObject object, Type type, JsonDeserializationContext context) {
		this.object = checkNotNull(object);
		this.type = type;
		this.context = context;
	}

	/** Returns the gson object. */
	public JsonObject getObject() {
		return object;
	}

	/** Returns the source object type. */
	public Type getType() {
		return type;
	}

	/** Returns the serialization context. */
	public JsonDeserializationContext getContext() {
		return context;
	}

	/** Returns a property, nullifying null values. */
	private JsonElement get(boolean required, String property) {
		JsonElement element = object.get(property);
		if (element != null && element.isJsonNull()) {
			element = null;
		}
		if (element == null) {
			throw new IllegalArgumentException(
					String.format("Missing required property [%s] for object [%s]", property, type));
		}
		return element;
	}

	/** Returns a string. */
	public String getString(boolean required, String property) {
		JsonElement element = get(required, property);
		return element != null ? element.getAsString() : null;
	}
	
	/** Returns a required string. */
	public String getString(String property) {
		return getString(true, property);
	}

	/** Returns an optional string. */
	public String getString(String property, @Nullable String defaultValue) {
		return valueOrDefault(getString(false, property), defaultValue);
	}
	
	/** Returns a string property. */
	public String getString(StringMetaProperty<? super T> property) {
		String defaultValue = property.getDefaultValue();
		if (defaultValue != null) {
			return getString(property.getName(), defaultValue);
		}
		return getString(property.isRequired(), property.getName());
	}

	/** Returns an optional string property. */
	public String getString(StringMetaProperty<? super T> property, @Nullable String defaultValue) {
		return getString(property.getName(), defaultValue);
	}
	

}
