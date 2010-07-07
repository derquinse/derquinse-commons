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

import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableMap;

/**
 * An immutable and thread-safe implementation of HierarchyMap.
 * @author Andres Rodriguez
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public abstract class ImmutableHierarchyMap<K, V> extends ForwardingMap<K, V> implements HierarchyMap<K, V> {
	/**
	 * Hierachy map preconditions.
	 * @param map Backing map.
	 * @param hierarchy Backing hierachy.
	 */
	static void check(Map<?, ?> map, Hierarchy<?> hierarchy) {
		checkNotNull(map, "The backing map must be provided.");
		checkNotNull(map, "The backing hierarchy must be provided.");
		checkArgument(map.keySet().equals(hierarchy.elements()), "Inconsistent map and hierarchy");
	}

	/**
	 * Returns an immutable hierarchy map backed by a map and a hierarchy.
	 * @param map Backing map.
	 * @param hierarchy Backing hierachy.
	 */
	public static <K, V> ImmutableHierarchyMap<K, V> of(Map<? extends K, ? extends V> map,
			Hierarchy<? extends K> hierarchy) {
		check(map, hierarchy);
		if (map.isEmpty()) {
			return of();
		}
		return new RegularImmutableHierarchyMap<K, V>(ImmutableMap.copyOf(map), ImmutableHierarchy.copyOf(hierarchy));
	}

	/**
	 * Returns an empty immutable hierarchy map.
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> ImmutableHierarchyMap<K, V> of() {
		return (ImmutableHierarchyMap<K, V>) EmptyImmutableHierarchyMap.INSTANCE;
	}

	/** Default constructor. */
	ImmutableHierarchyMap() {
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.HierarchyMap#apply(java.lang.Object)
	 */
	public V apply(K key) {
		final V value = get(checkNotNull(key));
		checkArgument(value != null);
		return value;
	}

	/**
	 * Builder for immutable hierarchies.
	 * @author Andres Rodriguez
	 * @param <K> Type of the keys.
	 * @param <V> Type of the values.
	 */
	public static final class Builder<K, V> implements Supplier<ImmutableHierarchyMap<K, V>> {
		private final ImmutableHierarchy.Builder<K> hierarchyBuilder;
		private final ImmutableMap.Builder<K, V> mapBuilder;

		/** Constructor. Use static factory method. */
		private Builder(boolean allowOutOfOrder) {
			this.hierarchyBuilder = ImmutableHierarchy.builder(allowOutOfOrder);
			this.mapBuilder = ImmutableMap.builder();
		}

		public boolean isAllowOutOfOrder() {
			return hierarchyBuilder.isAllowOutOfOrder();
		}

		public Builder<K, V> setAllowOutOfOrder(boolean allowOutOfOrder) {
			hierarchyBuilder.setAllowOutOfOrder(allowOutOfOrder);
			return this;
		}

		public Builder<K, V> add(K parent, K element) {
			hierarchyBuilder.add(parent, element);
			return this;
		}

		public Builder<K, V> addAll(K parent, Iterable<? extends K> elements) {
			hierarchyBuilder.addAll(parent, elements);
			return this;
		}

		public Builder<K, V> addAll(K parent, K... elements) {
			hierarchyBuilder.addAll(parent, elements);
			return this;
		}

		public Builder<K, V> addHierarchy(K parent, Hierarchy<? extends K> hierarchy, @Nullable K root, boolean includeRoot) {
			hierarchyBuilder.addHierarchy(parent, hierarchy, root, includeRoot);
			return this;
		}

		public <F> Builder<K, V> addHierarchy(K parent, Hierarchy<? extends F> hierarchy, @Nullable F root,
				boolean includeRoot, Function<? super F, K> function) {
			hierarchyBuilder.addHierarchy(parent, hierarchy, root, includeRoot, function);
			return this;
		}

		/**
		 * Associates {@code key} with {@code value} in the built map. Duplicate keys are not allowed,
		 * and will cause {@link #build} to fail.
		 */
		public Builder<K, V> put(K key, V value) {
			mapBuilder.put(key, value);
			return this;
		}

		/**
		 * Associates all of the given map's keys and values in the built map. Duplicate keys are not
		 * allowed, and will cause {@link #build} to fail.
		 * @throws NullPointerException if any key or value in {@code map} is null
		 */
		public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
			mapBuilder.putAll(map);
			return this;
		}

		/**
		 * Builds and returns an immutable hierarchy with the nodes added up to the method call.
		 * @returns An immutable hierarchy.
		 * @throws IllegalStateException if there are referenced parents that are not part of the
		 *           hierarchy yet.
		 */
		public ImmutableHierarchyMap<K, V> get() {
			return of(mapBuilder.build(), hierarchyBuilder.get());
		}

	}
}
