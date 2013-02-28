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

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * A metaclass field map implementation.
 * @author Andres Rodriguez
 * @param <T> Enclosing type.
 */
final class ImmutableFieldMap<T extends WithMetaClass> extends ForwardingMap<String, MetaField<? super T, ?>> implements
		FieldMap<T> {
	/** Empty map. */
	private static final ImmutableFieldMap<?> EMPTY = new ImmutableFieldMap<WithMetaClass>(
			ImmutableMap.<String, MetaField<? super WithMetaClass, ?>> of());
	/** Backing bimap. */
	private final ImmutableBiMap<String, MetaField<? super T, ?>> map;

	/** Returns the empty map. */
	@SuppressWarnings("unchecked")
	static <T extends WithMetaClass> ImmutableFieldMap<T> of() {
		return (ImmutableFieldMap<T>) EMPTY;
	}

	/** Returns a new builder. */
	static <T extends WithMetaClass> Builder<T> builder() {
		return new Builder<T>();
	}

	/** Constructor. */
	private ImmutableFieldMap(Map<String, MetaField<? super T, ?>> map) {
		this.map = ImmutableBiMap.copyOf(map);
	}

	@Override
	protected Map<String, MetaField<? super T, ?>> delegate() {
		return map;
	}

	/** Builder. */
	static final class Builder<T extends WithMetaClass> implements net.derquinse.common.base.Builder<ImmutableFieldMap<T>> {
		/** Builder map. */
		private final LinkedHashMap<String, MetaField<? super T, ?>> map = Maps.newLinkedHashMap();

		Builder() {
		}

		Builder<T> add(MetaField<? super T, ?> field) {
			map.put(field.getName(), field);
			return this;
		}

		Builder<T> addAll(Iterable<? extends MetaField<? super T, ?>> fields) {
			for (MetaField<? super T, ?> field : fields) {
				add(field);
			}
			return this;
		}

		Builder<T> addAll(FieldMap<? super T> fields) {
			for (MetaField<? super T, ?> field : fields.values()) {
				add(field);
			}
			return this;
		}
		
		/** Returns whether the builder is  urrently empty. */
		boolean isEmpty() {
			return map.isEmpty();
		}
	
		@Override
		public ImmutableFieldMap<T> build() {
			if (map.isEmpty()) {
				return of();
			}
			return new ImmutableFieldMap<T>(map);
		}
	}
}
