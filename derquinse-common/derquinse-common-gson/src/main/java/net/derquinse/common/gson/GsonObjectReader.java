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

import net.derquinse.common.meta.MetaFlag;
import net.derquinse.common.meta.MetaProperty;

import com.google.common.base.Optional;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Reader for gson object adapter support.
 * @author Andres Rodriguez
 */
public final class GsonObjectReader<T> {
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

	/** Returns a property. */
	public <V> Property<V> property(TypeToken<V> type, String name) {
		return new Property<V>(type, name);
	}

	/** Returns a property. */
	public <V> Property<V> property(MetaProperty<? super T, V> property) {
		return new Property<V>(property);
	}

	/** Returns a boolean flag. */
	public boolean flag(MetaFlag<? super T> flag) {
		return property(flag.asProperty()).get();
	}

	/**
	 * Object property.
	 * @param <V> Property type.
	 */
	public final class Property<V> {
		/** Property type. */
		private final TypeToken<V> type;
		/** Property name. */
		private final String name;
		/** Property default value. */
		private final V defaultValue;
		/** Whether the value is loaded. */
		private boolean loaded = false;
		/** Loaded value. */
		private V value;

		Property(TypeToken<V> type, String name) {
			this.type = checkNotNull(type, "The property type is required");
			this.name = checkNotNull(name, "The property name is required");
			this.defaultValue = null;
		}

		Property(MetaProperty<? super T, V> property) {
			checkNotNull(property, "The property descriptor is required");
			this.type = property.getFieldType();
			this.name = property.getName();
			this.defaultValue = property.getDefaultValue();
		}

		/** Loads the value, nullifying null elements. */
		private void load() {
			if (loaded) {
				return;
			}
			JsonElement element = object.get(name);
			if (element != null && element.isJsonNull()) {
				element = null;
			}
			if (element == null) {
				value = defaultValue;
			} else {
				value = context.deserialize(element, type.getType());
			}
		}

		/**
		 * Returns the required value of the property. If a metaproperty with a default value was used,
		 * such default value will be returned if the JSON object did not contain the property.
		 * @throws IllegalStateException if there is no value.
		 */
		public final V get() {
			load();
			if (value == null) {
				throw new IllegalStateException(String.format("Missing required property [%s] for object [%s]", name, type));
			}
			return value;
		}

		/**
		 * Returns the loaded value of the property or {@code null} if there is no value. If a
		 * metaproperty with a default value was used, such default value will be returned if the JSON
		 * object did not contain the property.
		 */
		public final V orNull() {
			load();
			return value;
		}

		/**
		 * Returns the loaded value of the property or the provided value if there is no value. If a
		 * metaproperty with a default value was used, such default value (instead of the provided one)
		 * will be returned if the JSON object did not contain the property.
		 */
		public final V or(@Nullable V defaultValue) {
			load();
			if (value == null) {
				return defaultValue;
			}
			return value;
		}

		/**
		 * Returns the loaded value of the property as an {@link Optional}. If a metaproperty with a
		 * default value was used, such default value will be returned if the JSON object did not
		 * contain the property.
		 */
		public final Optional<V> getOptional() {
			load();
			return Optional.fromNullable(value);
		}

	}

}
