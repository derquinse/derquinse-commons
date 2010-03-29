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

import java.util.List;
import java.util.Set;

/**
 * A hierarchy is a set of objects identified organized in a tree-like, where each level is an
 * ordered list. The first level of a hierarchy is the list of elements that have no parent. A
 * hierarchy is rooted if the first level has exactly one element, which is named the root element.
 * @author Andres Rodriguez
 * @param <E> Type of the elements in the hierarchy.
 * @see java.util.Set
 */
public interface Hierarchy<E> extends Set<E> {
	/**
	 * Returns the first level of the hierarchy.
	 * @return The list of values with no parent.
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
	 * @param element Element which children are requested.
	 * @return The list of children, the empty list if it has no children.
	 * @throws IllegalArgumentException if the specified element is not part of the hierarchy.
	 */
	List<E> getChildren(E element);

	/**
	 * Returns the parent of the specified element.
	 * @param element Element which parent is requested.
	 * @return The parent element or {@code null} if it is a first-level element.
	 * @throws IllegalArgumentException if the specified element is not part of the hierarchy.
	 */
	E getParent(E element);

	/**
	 * Returns all the descendants of the specified element.
	 * @param element Element which descendants are requested.
	 * @return All descendants. The iteration order is unspecified.
	 * @throws IllegalArgumentException if the specified value is not part of the hierarchy.
	 */
	Set<E> getDescendants(E element);

	/**
	 * Returns a subhierarchy if the current hierarchy. Implementations must specify if they provide a
	 * view or a new hierarchy.
	 * @param element Element in which the requested subhierarchy hierarchy starts.
	 * @param included If the provided element is to be included in the subhierarchy. In that case,
	 *          the subhierarchy is rooted with the provided element being the root.
	 * @return The requested subhierarchy.
	 * @throws IllegalArgumentException if the specified element is not part of the hierarchy.
	 */
	Hierarchy<E> getSubhierarchy(E element, boolean included);

}
