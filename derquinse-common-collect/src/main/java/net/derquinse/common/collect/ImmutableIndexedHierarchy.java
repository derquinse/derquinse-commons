/*
 * Copyright 2008-2010 the original author or authors.
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
package net.derquinse.common.collect;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Function;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;

/**
 * An immutable and thread-safe implementation of IndexedHierarchy.
 * @author Andres Rodriguez
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public abstract class ImmutableIndexedHierarchy<K, V> extends ForwardingHierarchy<V> implements IndexedHierarchy<K, V> {
	/**
	 * Preconditions.
	 * @param map Backing map.
	 * @param hierarchy Keys hierachy.
	 */
	static void check(BiMap<?, ?> map, Hierarchy<?> hierarchy) {
		checkNotNull(map, "The backing bimap must be provided.");
		checkNotNull(map, "The backing hierarchy must be provided.");
		checkArgument(map.keySet().equals(hierarchy.elementSet()), "Inconsistent bimap and hierarchy");
	}

	/**
	 * Returns an immutable indexed hierarchy backed by a bimap and a hierarchy.
	 * @param map Backing map.
	 * @param hierarchy Backing hierachy.
	 */
	public static <K, V> ImmutableIndexedHierarchy<K, V> of(BiMap<? extends K, ? extends V> map,
			Hierarchy<? extends K> hierarchy) {
		check(map, hierarchy);
		if (map.isEmpty()) {
			return of();
		}
		return new RegularImmutableIndexedHierarchy<K, V>(ImmutableBiMap.copyOf(map), ImmutableHierarchy.copyOf(hierarchy));
	}

	/**
	 * Builds an immutable indexed hierarchy from a set of values.
	 * @param elements Source elements.
	 * @param key Key function.
	 * @param parentKey Parent key function.
	 * @param value Value function.
	 */
	public static <T, K, V> ImmutableIndexedHierarchy<K, V> of(Iterable<? extends T> elements,
			Function<? super T, ? extends K> key, Function<? super T, ? extends K> parentKey,
			Function<? super T, ? extends V> value) {
		checkNotNull(elements, "The source elements must be provided.");
		checkNotNull(key, "The key function must be provided.");
		checkNotNull(parentKey, "The parent key function must be provided.");
		checkNotNull(value, "The value function must be provided.");
		ImmutableHierarchy.Builder<K> h = ImmutableHierarchy.builder(true);
		ImmutableBiMap.Builder<K, V> m = ImmutableBiMap.builder();
		for (T element : elements) {
			final K k = key.apply(element);
			m.put(k, value.apply(element));
			h.add(parentKey.apply(element), k);
		}
		final ImmutableBiMap<K, V> biMap = m.build();
		if (biMap.isEmpty()) {
			return of();
		}
		return new RegularImmutableIndexedHierarchy<K, V>(biMap, h.get());
	}

	/**
	 * Returns an empty immutable hierarchy map.
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> ImmutableIndexedHierarchy<K, V> of() {
		return (ImmutableIndexedHierarchy<K, V>) EmptyImmutableIndexedHierarchy.INSTANCE;
	}

	/** Default constructor. */
	ImmutableIndexedHierarchy() {
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.IndexedHierarchy#apply(java.lang.Object)
	 */
	public V apply(K key) {
		final V value = asMap().get(checkNotNull(key));
		checkArgument(value != null);
		return value;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.IndexedHierarchy#asMap()
	 */
	public abstract ImmutableBiMap<K, V> asMap();

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#elementSet()()
	 */
	public ImmutableSet<V> elementSet() {
		return asMap().values();
	}
}
