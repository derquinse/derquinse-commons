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

import javax.annotation.Nullable;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;

/**
 * An indexed hierarchy is a hierarchy with a primary index (so the keys have the same hierarchy).
 * @author Andres Rodriguez
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 * @see java.util.Map
 * @see Hierarchy
 */
@Beta
public interface IndexedHierarchy<K, V> extends Hierarchy<V>, Function<K, V> {
	/**
	 * Applies the function to an object of type {@code F}, resulting in an object of type {@code T}.
	 * Note that types {@code F} and {@code T} may or may not be the same.
	 * @param from the source object
	 * @return the resulting object
	 */
	V apply(@Nullable K from);

	/**
	 * Returns <tt>true</tt> if this map contains a mapping for the specified key. More formally,
	 * returns <tt>true</tt> if and only if this map contains a mapping for a key <tt>k</tt> such that
	 * <tt>(key==null ? k==null : key.equals(k))</tt>. (There can be at most one such mapping.)
	 * @param key key whose presence in this map is to be tested
	 * @return <tt>true</tt> if this map contains a mapping for the specified key
	 * @throws ClassCastException if the key is of an inappropriate type for this map (optional)
	 * @throws NullPointerException if the specified key is null and this map does not permit null
	 *           keys (optional)
	 */
	boolean containsKey(Object key);

	/**
	 * Returns the value to which the specified key is mapped, or {@code null} if this map contains no
	 * mapping for the key.
	 * <p>
	 * More formally, if this map contains a mapping from a key {@code k} to a value {@code v} such
	 * that {@code (key==null ? k==null : key.equals(k))}, then this method returns {@code v};
	 * otherwise it returns {@code null}. (There can be at most one such mapping.)
	 * <p>
	 * If this map permits null values, then a return value of {@code null} does not
	 * <i>necessarily</i> indicate that the map contains no mapping for the key; it's also possible
	 * that the map explicitly maps the key to {@code null}. The {@link #containsKey containsKey}
	 * operation may be used to distinguish these two cases.
	 * @param key the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or {@code null} if this map contains no
	 *         mapping for the key
	 * @throws ClassCastException if the key is of an inappropriate type for this map (optional)
	 * @throws NullPointerException if the specified key is null and this map does not permit null
	 *           keys (optional)
	 */
	V get(Object key);

}
