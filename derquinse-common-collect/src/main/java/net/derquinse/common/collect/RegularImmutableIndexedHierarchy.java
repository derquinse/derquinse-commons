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

import com.google.common.collect.ImmutableBiMap;

/**
 * An immutable and thread-safe implementation of IndexedHierarchy.
 * @author Andres Rodriguez
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
final class RegularImmutableIndexedHierarchy<K, V> extends ImmutableIndexedHierarchy<K, V> {
	/** Backing map. */
	private final ImmutableBiMap<K, V> map;
	/** Keys hierarchy. */
	private final ImmutableHierarchy<K> keys;
	/** Values hierarchy. */
	private final ImmutableHierarchy<V> values;
	/** Inverse hierarchy. */
	private final Inverse inverse = new Inverse();

	/**
	 * Constructor.
	 * @param map Backing map.
	 * @param keys Keys hierarchy.
	 */
	RegularImmutableIndexedHierarchy(ImmutableBiMap<K, V> map, ImmutableHierarchy<K> keys) {
		check(map, keys);
		this.map = map;
		ImmutableHierarchy.Builder<V> builder = ImmutableHierarchy.builder(true);
		for (K key : keys) {
			final K parentKey = keys.getParent(key);
			final V parentValue = parentKey != null ? map.get(parentKey) : null;
			builder.add(parentValue, map.get(key));
		}
		this.values = builder.get();
		this.keys = keys;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.ForwardingHierarchy#delegate()
	 */
	@Override
	protected Hierarchy<V> delegate() {
		return values;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.ImmutableIndexedHierarchy#asMap()
	 */
	@Override
	public ImmutableBiMap<K, V> asMap() {
		return map;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.IndexedHierarchy#inverse()
	 */
	public IndexedHierarchy<V, K> inverse() {
		return inverse;
	}

	/* Inverse hierarchy. */
	private final class Inverse extends ImmutableIndexedHierarchy<V, K> {
		/*
		 * (non-Javadoc)
		 * @see net.derquinse.common.collect.ForwardingHierarchy#delegate()
		 */
		@Override
		protected Hierarchy<K> delegate() {
			return keys;
		}

		/*
		 * (non-Javadoc)
		 * @see net.derquinse.common.collect.ImmutableIndexedHierarchy#asMap()
		 */
		@Override
		public ImmutableBiMap<V, K> asMap() {
			return map.inverse();
		}

		/*
		 * (non-Javadoc)
		 * @see net.derquinse.common.collect.IndexedHierarchy#inverse()
		 */
		public IndexedHierarchy<K, V> inverse() {
			return RegularImmutableIndexedHierarchy.this;
		}
	}

}
