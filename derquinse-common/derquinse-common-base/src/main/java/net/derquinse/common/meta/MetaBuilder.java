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

import net.derquinse.common.base.Builder;

/**
 * Meta builder interface.
 * @author Andres Rodriguez
 * @param <T> Described type.
 */
public interface MetaBuilder<T extends WithMetaClass> extends Builder<T> {
	/** Returns the metaclass of the object to build. */
	MetaClass<T> getMetaClass();

	/**
	 * Sets a property value.
	 * @param property Property to set.
	 * @param value Value to set.
	 * @return This builder for method chaining.
	 * @throws IllegalArgumentException if the property is not defined for the meta class.
	 * @throws IllegalArgumentException If the value is not allowed for the property.
	 * @throws IllegalStateException If the value has already been set.
	 */
	<V> MetaBuilder<T> set(MetaProperty<? super T, V> property, V value);

	/**
	 * Sets a flag value.
	 * @param flag Flag to set.
	 * @param value Value to set.
	 * @return This builder for method chaining.
	 * @throws IllegalArgumentException if the property is not defined for the meta class.
	 * @throws IllegalArgumentException If the value is not allowed for the property.
	 * @throws IllegalStateException If the value has already been set.
	 */
	<V> MetaBuilder<T> set(MetaFlag<? super T> property, V value);

}
