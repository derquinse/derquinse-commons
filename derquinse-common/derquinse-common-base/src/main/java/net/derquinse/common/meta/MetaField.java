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
 * Base class for property and flag descriptors. Meta fields use identity equality.
 * @author Andres Rodriguez
 * @param <C> Containing type.
 * @param <T> Field type.
 */
public abstract class MetaField<C, T> implements WithNameProperty {
	/** Enclosing type. */
	@SuppressWarnings("serial")
	private final transient TypeToken<C> enclosingType = new TypeToken<C>(getClass()) {};
	/** Field type. */
	@SuppressWarnings("serial")
	private final transient TypeToken<T> fieldType = new TypeToken<T>(getClass()) {};
	/** Field name. */
	private final String name;

	/**
	 * Default constructor.
	 * @param name Property name.
	 */
	MetaField(String name) {
		this.name = checkNotNull(name, "The name property must be provided");
	}

	/** Returns the enclosing type. */
	public final TypeToken<C> getEnclosingType() {
		return enclosingType;
	}
	
	/** Returns the field type. */
	public final TypeToken<T> getFieldType() {
		return fieldType;
	}

	/**
	 * Returns the field name.
	 * @return The field name.
	 */
	@Override
	public final String getName() {
		return name;
	}

	/** Uses identity hash code. */
	@Override
	public final int hashCode() {
		return super.hashCode();
	}

	/** Uses identity equality. */
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public String toString() {
		return Metas.toStringHelper(this).add(NAME).toString();
	}
}
