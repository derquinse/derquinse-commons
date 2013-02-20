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
package net.derquinse.common.meta;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import net.derquinse.common.base.Builder;

import com.google.common.collect.Maps;

/**
 * Meta builder class.
 * @author Andres Rodriguez
 * @param <T> Built type.
 */
public abstract class MetaBuilder<T extends WithMetaClass> implements Builder<T> {
	/** Meta class. */
	private final MetaClass<T> metaClass;
	/** Property value map. */
	private final Map<MetaField<? super T>, Object> values = Maps.newHashMap();

	MetaBuilder(MetaClass<T> metaClass) {
		this.metaClass = checkNotNull(metaClass);
	}

	final void checkField(MetaField<? super T> field) {
		if (!metaClass.getFields().containsValue(field)) {
			throw new IllegalArgumentException(String.format("MetaClass [%s] does not contain field [%s]", metaClass, field));
		}
	}

	final void checkFieldNotSet(MetaField<? super T> field) {
		checkField(field);
		if (values.containsKey(field)) {
			throw new IllegalArgumentException(String.format("Value already set for field [%s] of MetaClass [%s]", field,
					metaClass));
		}
	}

	/** Returns the metaclass of the object to build. */
	public final MetaClass<T> getMetaClass() {
		return metaClass;
	}

	/**
	 * Sets a property value.
	 * @param property Property to set.
	 * @param value Value to set.
	 * @return This builder for method chaining.
	 * @throws IllegalArgumentException if the property is not defined for the meta class.
	 * @throws IllegalArgumentException If the value is not allowed for the property.
	 * @throws IllegalStateException If the value has already been set.
	 */
	public final <V> MetaBuilder<T> set(MetaProperty<? super T, V> property, V value) {
		checkFieldNotSet(property);
		property.checkValue(value);
		values.put(property, value);
		return this;
	}

	/**
	 * Sets a flag value.
	 * @param flag Flag to set.
	 * @param value Value to set.
	 * @return This builder for method chaining.
	 * @throws IllegalArgumentException if the property is not defined for the meta class.
	 * @throws IllegalArgumentException If the value is not allowed for the property.
	 * @throws IllegalStateException If the value has already been set.
	 */
	public final MetaBuilder<T> set(MetaFlag<? super T> flag, boolean value) {
		checkFieldNotSet(flag);
		values.put(flag, value);
		return this;
	}

}
