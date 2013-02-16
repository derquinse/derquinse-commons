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

import com.google.common.reflect.TypeToken;

/**
 * Base class for property and flag descriptors.
 * @author Andres Rodriguez
 * @param <C> Containing type.
 */
public abstract class Meta<C> implements NameProperty {
	/** Enclosing type. */
	@SuppressWarnings("serial")
	private final transient TypeToken<C> enclosingType = new TypeToken<C>(getClass()) {};
	/** Property name. */
	private final String name;

	/**
	 * Default constructor.
	 * @param name Property name.
	 */
	Meta(String name) {
		this.name = checkNotNull(name, "The name property must be provided");
	}

	/** Returns the enclosing type. */
	public final TypeToken<C> getEnclosingType() {
		return enclosingType;
	}

	/**
	 * Returns the property name.
	 * @return The property name.
	 */
	@Override
	public final String getName() {
		return name;
	}

	@Override
	public String toString() {
		return Metas.toStringHelper(this).add(NAME).toString();
	}
}
