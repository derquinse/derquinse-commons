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

import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * An immutable and thread-safe implementation of HierarchyMap.
 * @author Andres Rodriguez
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
final class RegularImmutableHierarchyMap<K, V> extends ImmutableHierarchyMap<K, V> {
	/** Backing map. */
	private final ImmutableMap<K, V> map;
	/** Backing hierarchy. */
	private final ImmutableHierarchy<K> hierarchy;

	/**
	 * Constructor.
	 * @param map Backing map.
	 * @param hierarchy Backing hierachy.
	 */
	RegularImmutableHierarchyMap(ImmutableMap<K, V> map, ImmutableHierarchy<K> hierarchy) {
		check(map, hierarchy);
		this.map = map;
		this.hierarchy = hierarchy;
	}

	@Override
	protected Map<K, V> delegate() {
		return map;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.HierarchyMap#keyHierarchy()
	 */
	public Hierarchy<K> keyHierarchy() {
		return hierarchy;
	}

}
