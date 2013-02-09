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

/**
 * Property name property.
 * @author Andres Rodriguez
 * @param <C> Containing type.
 */
public interface NameProperty {
	/** Descriptor for name property. */
	StringMetaProperty<NameProperty> NAME = new StringMetaProperty<NameProperty>("name", true) {
		public String apply(NameProperty input) {
			return input.getName();
		}
	};

	/**
	 * Returns the property name.
	 * @return The property name.
	 */
	String getName();
}
