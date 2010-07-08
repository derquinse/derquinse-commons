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

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.collect.BiMap;

/**
 * An indexed hierarchy is a hierarchy with a primary index (so the keys have the same hierarchy).
 * As the elements of a hierarchy form a set, the keys and the values form a bimap.
 * @author Andres Rodriguez
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @see com.google.common.collect.BiMap
 * @see Hierarchy
 */
@Beta
public interface IndexedHierarchy<K, V> extends Hierarchy<V>, Function<K, V> {
	/**
	 * Returns the value to which the specified key is mapped.
	 * @param key The key whose associated value is to be returned.
	 * @return The associated value.
	 * @throws NullPointerException if the argument is {@code null}.
	 * @throws IllegalArgumentException if key is not contained in the map.
	 */
	V apply(K key);

	/**
	 * Returns a view of the hieratchy as a bimap.
	 * @return A bimap with the entries of the hierarchy.
	 */
	BiMap<K, V> asMap();

	/**
	 * Returns the inverse view of this indexed hierarchy.
	 * @return The inverse view of this indexed hierarchy.
	 */
	IndexedHierarchy<V, K> inverse();

}
