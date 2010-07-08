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

import com.google.common.collect.BiMap;

/**
 * An indexed hierarchy that forwards its method calls to a delegated one.
 * @author Andres Rodriguez
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public abstract class ForwardingIndexedHierarchy<K, V> extends ForwardingHierarchy<V> implements IndexedHierarchy<K, V> {
	public ForwardingIndexedHierarchy() {
	}

	@Override
	protected abstract IndexedHierarchy<K, V> delegate();

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.IndexedHierarchy#apply(java.lang.Object)
	 */
	public V apply(K key) {
		return delegate().apply(key);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.IndexedHierarchy#asMap()
	 */
	public BiMap<K, V> asMap() {
		return delegate().asMap();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.IndexedHierarchy#inverse()
	 */
	public IndexedHierarchy<V, K> inverse() {
		return delegate().inverse();
	}

}
