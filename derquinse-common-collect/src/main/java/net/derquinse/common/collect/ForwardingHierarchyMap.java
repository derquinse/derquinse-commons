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

import com.google.common.collect.ForwardingMap;

/**
 * A hierarchy map that forwards its method calls to a delegated one.
 * @author Andres Rodriguez
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
public abstract class ForwardingHierarchyMap<K, V> extends ForwardingMap<K, V> implements HierarchyMap<K, V> {
	public ForwardingHierarchyMap() {
	}

	@Override
	protected abstract HierarchyMap<K, V> delegate();

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.HierarchyMap#apply(java.lang.Object)
	 */
	public V apply(K key) {
		return delegate().apply(key);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.HierarchyMap#keyHierarchy()
	 */
	public Hierarchy<K> keyHierarchy() {
		return delegate().keyHierarchy();
	}

}
