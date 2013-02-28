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

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;

/**
 * Meta class descriptors.
 * @author Andres Rodriguez
 * @param <T> Described type.
 */
public final class MetaClass<T extends WithMetaClass> {
	/** Type map. */
	private static final MetaClassMap map = new MetaClassMap();
	/** Base meta class. */
	static final MetaClass<WithMetaClass> ROOT = new MetaClass<WithMetaClass>(WithMetaClass.class);

	/**
	 * Returns the metaclass for a type, discovering it if needed.
	 * @param type Type.
	 * @return The meta class.
	 */
	public static <T extends WithMetaClass> MetaClass<T> of(Class<T> type) {
		return of(TypeToken.of(type));
	}

	/**
	 * Returns the metaclass for a type.
	 * @param type Type.
	 * @return The meta class.
	 */
	private static <T extends WithMetaClass> MetaClass<T> of(TypeToken<T> type) {
		MetaClass<T> metaClass = map.get(type);
		if (metaClass == null) {
			metaClass = new MetaClassBuilder<T>(type).build();
		}
		return metaClass;
	}

	/**
	 * Registers a metaclass for a type. If the type was already registered a warning is logged and an
	 * exception is thrown.
	 * @throws DuplicateMetaClassException if there was a previous entry.
	 */
	private static <T extends WithMetaClass> void register(MetaClass<T> metaClass) {
		map.put(metaClass);
	}

	/**
	 * Returns a builder for a new class.
	 * @throws IllegalStateException the type is already registered.
	 */
	public static <T extends WithMetaClass> MetaClassBuilder<T> builder(Class<T> type) {
		return new MetaClassBuilder<T>(TypeToken.of(type));
	}

	/** Described type. */
	private final TypeToken<T> type;
	/** Super class. */
	private final MetaClass<? super T> superclass;
	/** Super interfaces. */
	private final ImmutableList<MetaClass<? super T>> superinterfaces;
	/** Declared field map. */
	private final FieldMap<T> declaredFields;
	/** All field map. */
	private final FieldMap<T> fields;

	/** Root constructor. */
	private MetaClass(Class<T> rootClass) {
		this.type = TypeToken.of(rootClass);
		this.superclass = this;
		this.superinterfaces = ImmutableList.of();
		this.declaredFields = ImmutableFieldMap.of();
		this.fields = ImmutableFieldMap.of();
		register(this);
	}

	/**
	 * Default constructor.
	 */
	MetaClass(MetaClassBuilder<T> builder) {
		this.type = builder.getType();
		this.superclass = builder.getSuperclass();
		this.superinterfaces = builder.getSuperinterfaces();
		this.declaredFields = builder.getDeclaredFields();
		this.fields = builder.getFields();
		register(this);
	}
	
	/** Returns whether is the root metaclass. */
	public boolean isRoot() {
		return this == ROOT;
	}

	/** Returns the enclosing type. */
	public final TypeToken<T> getType() {
		return type;
	}

	/** Returns the supermetaclass. */
	public MetaClass<? super T> getSuperclass() {
		return superclass;
	}

	/** Returns the superinterfaces. */
	public ImmutableList<MetaClass<? super T>> getSuperinterfaces() {
		return superinterfaces;
	}

	/** Returns the declared fields. */
	public final FieldMap<T> getDeclaredFields() {
		return declaredFields;
	}

	/** Returns the all fields map. */
	public FieldMap<T> getFields() {
		return fields;
	}

	@Override
	public String toString() {
		return Metas.toStringHelper(this).toString();
	}
}
