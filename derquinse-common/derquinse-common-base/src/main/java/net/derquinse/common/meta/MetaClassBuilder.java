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
import static net.derquinse.common.meta.MetaClass.ROOT;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

import net.derquinse.common.base.Builder;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;

/**
 * Meta class descriptor builder.
 * @author Andres Rodriguez
 * @param <T> Described type.
 */
public final class MetaClassBuilder<T extends WithMetaClass> implements Builder<MetaClass<T>> {
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

	private static boolean publicStaticFinal(Member m) {
		int modifiers = m.getModifiers();
		return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
	}

	/**
	 * Default constructor.
	 */
	MetaClassBuilder(TypeToken<T> type) {
		this.type = checkNotNull(type, "The described type is mandatory");
		// Discover the class
		// 1 - Superclass
		Class<? super T> raw = type.getRawType();
		this.superclass = getSuper(raw.getSuperclass());
		ImmutableList.Builder<MetaClass<? super T>> builder = ImmutableList.builder();
		// 2 - Superinterfaces
		for (Class<?> i : raw.getInterfaces()) {
			MetaClass<? super T> meta = getSuper(i);
			if (meta != ROOT) {
				builder.add(meta);
			}
		}
		this.superinterfaces = builder.build();
		// 3 - Declared fields
		ImmutableFieldMap.Builder<T> fieldBuilder = ImmutableFieldMap.builder();
		for (Field f : raw.getDeclaredFields()) {
			// We get public static final MetaFields that are type compatible.
			if (publicStaticFinal(f)) {
				Object val;
				try {
					val = f.get(null);
				} catch (Exception e) {
					throw new IllegalArgumentException(String.format("Unable to get value of type [%s] field [%s]", type,
							f.getName()), e);
				}
				if (val instanceof MetaField<?, ?>) {
					MetaField<?, ?> mf = MetaField.class.cast(val);
					if (mf.getEnclosingType().isAssignableFrom(type)) {
						// Checked in the if
						@SuppressWarnings("unchecked")
						MetaField<? super T, ?> smf = (MetaField<? super T, ?>) mf;
						fieldBuilder.add(smf);
					}
				}
			}
		}
		this.declaredFields = fieldBuilder.build();
		// 4 - All fields
		ImmutableFieldMap.Builder<T> fmap = ImmutableFieldMap.builder();
		fmap.addAll(this.superclass.getFields());
		for (MetaClass<? super T> mi : this.superinterfaces) {
			fmap.addAll(mi.getFields());
		}
		if (fmap.isEmpty()) {
			this.fields = declaredFields;
		} else {
			fmap.addAll(declaredFields);
			this.fields = fmap.build();
		}
	}

	/** Returns the metaclass for the superclass or a superinterface. */
	@SuppressWarnings("unchecked")
	private MetaClass<? super T> getSuper(Class<?> klass) {
		if (klass == null || !WithMetaClass.class.isAssignableFrom(klass)) {
			return ROOT;
		}
		// We know what we are doing
		return (MetaClass<? super T>) MetaClass.of(klass.asSubclass(WithMetaClass.class));
	}

	/** Returns the described type. */
	public TypeToken<T> getType() {
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

	/** Returns the declared field map. */
	public FieldMap<T> getDeclaredFields() {
		return declaredFields;
	}

	/** Returns the all fields map. */
	public FieldMap<T> getFields() {
		return fields;
	}

	/**
	 * Builds the meta class. If there was a previous metaclass registered for the same type, that one
	 * is returned.
	 */
	public MetaClass<T> build() {
		try {
			return new MetaClass<T>(this);
		} catch (DuplicateMetaClassException e) {
			// Trusted
			@SuppressWarnings("unchecked")
			MetaClass<T> metaClass = (MetaClass<T>) e.getMetaClass();
			return metaClass;
		}
	}

	@Override
	public String toString() {
		return Metas.toStringHelper(this).toString();
	}
}
