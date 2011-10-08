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
package net.derquinse.common.property;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Base class for property and flag descriptors.
 * @author Andres Rodriguez
 * @param <C> Containing type.
 */
public abstract class Meta<C> {
	/** Descriptor for name property. */
	public static final StringMetaProperty<Meta<?>> NAME = new StringMetaProperty<Meta<?>>("name", true) {
		public String apply(Meta<?> input) {
			return input.getName();
		}
	};

	/** Property name. */
	private final String name;

	/**
	 * Default constructor.
	 * @param name Property name.
	 */
	Meta(String name) {
		this.name = checkNotNull(name, "The name property must be provided");
	}

	/**
	 * Returns the property name.
	 * @return The property name.
	 */
	public final String getName() {
		return name;
	}
}
