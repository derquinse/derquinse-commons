/*
 * Copyright (C) the original author or authors.
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

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.annotations.Beta;

/**
 * A hierarchy is a set of objects identified organized in a tree-like structure, where each level
 * is an ordered list. The first level of a hierarchy is the list of elements that have no parent. A
 * hierarchy is rooted if the first level has exactly one element, which is named the root element.
 * Null elements are not allowed. As only an immutable implementation is provided, mutable semantics
 * are not specified yet.
 * <p>
 * All methods throw {@code NullPointerException} if a {@code null} value is passed as an argument
 * not marked as nullable.
 * @author Andres Rodriguez
 * @param <E> Type of the elements in the hierarchy.
 * @see java.util.Set
 */
@Beta
public interface Hierarchy<E> {
	/** Returns {@code true} if the hierarchy is empty. */
	boolean isEmpty();

	/** Returns the number of elements in the hierarchy. */
	int size();

	/** Returns {@code true} if the hierarchy contains the provided element. */
	boolean contains(E element);

	/** Returns {@code true} if the hierarchy contains all the provided elements. */
	boolean containsAll(Iterable<? extends E> elements);

	/**
	 * Returns the set of distinct elements contained in this hierarchy.
	 * @return A set containing a live view the elements of the hierarchy.
	 */
	Set<E> elementSet();

	/**
	 * Returns the first level of the hierarchy. For a rooted hierarchy it will be a single list
	 * element.
	 * @return The list of values with no parent. Never {@code null}.
	 */
	List<E> getFirstLevel();

	/**
	 * Returns whether the hierarchy is rooted.
	 * @return True if the first level has one element.
	 */
	boolean isRooted();

	/**
	 * Returns the root element.
	 * @return The only element in the first level.
	 * @throws IllegalStateException if the hierarchy is not rooted.
	 */
	E getRoot();

	/**
	 * Returns the children of the specified element.
	 * @param element Element which children are requested. If {@code null} the first level will be
	 *          returned.
	 * @return The list of children, the empty list if it has no children. Never {@code null}.
	 * @throws IllegalArgumentException if the specified element is not part of the hierarchy.
	 */
	List<E> getChildren(@Nullable E element);

	/**
	 * Returns the parent of the specified element.
	 * @param element Element which parent is requested.
	 * @return The parent element or {@code null} if it is a first-level element.
	 * @throws IllegalArgumentException if the specified element is not part of the hierarchy.
	 */
	E getParent(E element);

	/**
	 * Returns the siblings of the specified element.
	 * @param element Element which siblings are requested.
	 * @return The list of siblings. At least with one element, the argument itself.
	 * @throws IllegalArgumentException if the specified element is not part of the hierarchy.
	 */
	List<E> getSiblings(E element);

	/**
	 * Returns the index of the specified element among its siblings.
	 * @param element Element which index is requested.
	 * @return The position in the list of siblings.
	 * @throws IllegalArgumentException if the specified element is not part of the hierarchy.
	 */
	int getIndex(E element);

	/**
	 * Returns the ancestors of the specified element.
	 * @param element Element which ancestors are requested.
	 * @return The ancestors in descending level order.
	 * @throws IllegalArgumentException if the specified element is not part of the hierarchy.
	 */
	Iterable<E> getAncestors(E element);

	/**
	 * Compares the specified object with this hierarchy for equality. Returns <tt>true</tt> if the
	 * specified object is also a hierarchy, the two hierarchies have the same size, and both contain
	 * the same elements in the same coordinates. This definition ensures that the equals method works
	 * properly across different implementations of the hierarchy interface.
	 * @param o Object to be compared for equality with this hierarchy.
	 * @return True if the specified object is equal to this hierarchy.
	 */
	boolean equals(@Nullable Object o);

	/**
	 * Returns the hash code value for this hierarchy. The hash code of a hierarchy is defined to be
	 * the sum of hash(e)^level(e) for every element e of the hierarchy. This definition fulfills the
	 * general contract of {@link Object#hashCode}.
	 * @return The hash code value for this hierarchy.
	 * @see Object#equals(Object)
	 * @see Hierarchy#equals(Object)
	 */
	int hashCode();

}
