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

import java.lang.reflect.Type;

import javax.annotation.concurrent.NotThreadSafe;

import net.derquinse.common.meta.BooleanMetaProperty;
import net.derquinse.common.meta.CharacterMetaProperty;
import net.derquinse.common.meta.DoubleMetaProperty;
import net.derquinse.common.meta.FloatMetaProperty;
import net.derquinse.common.meta.IntegerMetaProperty;
import net.derquinse.common.meta.LongMetaProperty;
import net.derquinse.common.meta.MetaFlag;
import net.derquinse.common.meta.MetaProperty;
import net.derquinse.common.meta.ShortMetaProperty;
import net.derquinse.common.meta.StringMetaProperty;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

/**
 * Writer for gson object adapter support.
 * @author Andres Rodriguez
 */
@NotThreadSafe
public final class GsonObjectWriter<T> {
	/** Object to write. */
	private final T source;
	/** Source object type. */
	private final Type type;
	/** Serialization context. */
	private final JsonSerializationContext context;
	/** Gson object. */
	private final JsonObject object = new JsonObject();
	/** Whether nulls are serialized. */
	private boolean nulls = false;

	/** Constructor. */
	GsonObjectWriter(T src, Type typeOfSrc, JsonSerializationContext context) {
		this.source = src;
		this.type = typeOfSrc;
		this.context = context;
	}

	/** Returns the object to write. */
	public T getSource() {
		return source;
	}

	/** Returns the object type. */
	public Type getType() {
		return type;
	}

	/** Returns the serialization context. */
	public JsonSerializationContext getContext() {
		return context;
	}

	/** Returns the gson object. */
	public JsonObject getObject() {
		return object;
	}

	/**
	 * Configures the writer the serialize null values. Null values will only be serialized if the
	 * underlying Gson object is also configured to do so.
	 */
	public void serializeNulls() {
		this.nulls = true;
	}

	/** Adds a string property. */
	public GsonObjectWriter<T> add(String property, String value) {
		if (nulls || value != null) {
			object.addProperty(property, value);
		}
		return this;
	}

	/** Adds a boolean property. */
	public GsonObjectWriter<T> add(String property, Boolean value) {
		if (nulls || value != null) {
			object.addProperty(property, value);
		}
		return this;
	}

	/** Adds a character property. */
	public GsonObjectWriter<T> add(String property, Character value) {
		if (nulls || value != null) {
			object.addProperty(property, value);
		}
		return this;
	}

	/** Adds a number property. */
	public GsonObjectWriter<T> add(String property, Number value) {
		if (nulls || value != null) {
			object.addProperty(property, value);
		}
		return this;
	}

	/** Adds an object property, serializing it first. */
	public GsonObjectWriter<T> add(String property, Object value) {
		if (nulls || value != null) {
			object.add(property, context.serialize(value));
		}
		return this;
	}

	/** Adds an object property, serializing it first. */
	public GsonObjectWriter<T> add(String property, Object value, Type type) {
		if (nulls || value != null) {
			object.add(property, context.serialize(value, type));
		}
		return this;
	}

	/** Adds a string property. */
	public GsonObjectWriter<T> add(StringMetaProperty<? super T> property) {
		return add(property.getName(), property.apply(source));
	}

	/** Adds an integer property. */
	public GsonObjectWriter<T> add(IntegerMetaProperty<? super T> property) {
		return add(property.getName(), property.apply(source));
	}

	/** Adds a long property. */
	public GsonObjectWriter<T> add(LongMetaProperty<? super T> property) {
		return add(property.getName(), property.apply(source));
	}

	/** Adds a short property. */
	public GsonObjectWriter<T> add(ShortMetaProperty<? super T> property) {
		return add(property.getName(), property.apply(source));
	}

	/** Adds a float property. */
	public GsonObjectWriter<T> add(FloatMetaProperty<? super T> property) {
		return add(property.getName(), property.apply(source));
	}

	/** Adds a double property. */
	public GsonObjectWriter<T> add(DoubleMetaProperty<? super T> property) {
		return add(property.getName(), property.apply(source));
	}

	/** Adds a character property. */
	public GsonObjectWriter<T> add(CharacterMetaProperty<? super T> property) {
		return add(property.getName(), property.apply(source));
	}

	/** Adds a boolean property. */
	public GsonObjectWriter<T> add(BooleanMetaProperty<? super T> property) {
		return add(property.getName(), property.apply(source));
	}

	/** Adds a boolean property. */
	public GsonObjectWriter<T> add(MetaFlag<? super T> property) {
		return add(property.getName(), property.apply(source));
	}

	/** Adds an object property. */
	public GsonObjectWriter<T> add(MetaProperty<? super T, ?> property) {
		return add(property.getName(), property.apply(source));
	}

	/** Adds an object property. */
	public GsonObjectWriter<T> add(MetaProperty<? super T, ?> property, Type type) {
		return add(property.getName(), property.apply(source), type);
	}

}
