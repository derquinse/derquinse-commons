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

import com.google.common.annotations.Beta;
import com.google.common.base.Function;

/**
 * A hierarchy map is a map which keys form a hierarchy.
 * @author Andres Rodriguez
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @see java.util.Map
 * @see Hierarchy
 */
@Beta
public interface HierarchyMap<K, V> extends Map<K, V>, Function<K, V> {
	/**
	 * Returns the hierarchy of keys.
	 * @The hierarchy of keys.
	 */
	Hierarchy<K> keyHierarchy();

	/**
	 * Returns the value to which the specified key is mapped.
	 * @param key The key whose associated value is to be returned.
	 * @return The associated value.
	 * @throws NullPointerException if the argument is {@code null}.
	 * @throws IllegalArgumentException if key is not contained in the map.
	 */
	V apply(K key);
}
