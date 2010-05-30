/*
 * Copyright 2008 the original author or authors.
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
 * An empty immutable hierarchy map.
 * @author Andres Rodriguez
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
final class EmptyImmutableHierarchyMap extends ImmutableHierarchyMap<Object, Object> {
	static final EmptyImmutableHierarchyMap INSTANCE = new EmptyImmutableHierarchyMap();

	private EmptyImmutableHierarchyMap() {
	}

	@Override
	protected Map<Object, Object> delegate() {
		return ImmutableMap.of();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.HierarchyMap#keyHierarchy()
	 */
	public Hierarchy<Object> keyHierarchy() {
		return ImmutableHierarchy.of();
	}

}
