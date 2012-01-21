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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nullable;

import net.derquinse.common.base.NotInstantiable;

import com.google.common.annotations.Beta;

/**
 * Support methods for hierarchies.
 * @author Andres Rodriguez
 */
@Beta
public final class Hierarchies extends NotInstantiable {
	/** Not instantiable. */
	private Hierarchies() {
	}

	private static <E> Hierarchy<E> check(Hierarchy<E> hierarchy) {
		return checkNotNull(hierarchy, "The hierarchy is required");
	}

	private static <E> HierarchyVisitor<E> check(HierarchyVisitor<E> visitor) {
		return checkNotNull(visitor, "The visitor is required");
	}

	/**
	 * Traverses the hierarchy depth-first.
	 * @param hierarchy Hierarchy to visit.
	 * @param visitor Visitor.
	 */
	public static <E> void visitDepthFirst(Hierarchy<E> hierarchy, HierarchyVisitor<? super E> visitor) {
		visitDepthFirst(check(hierarchy), null, hierarchy.getFirstLevel(), check(visitor));
	}

	/**
	 * Traverses the hierarchy depth-first, starting in the specified element.
	 * @param hierarchy Hierarchy to visit.
	 * @param visitor Visitor.
	 * @param element Starting element. If {@code null} the call is equivalent to
	 *          {@code visitDepthFirst(visitor)}.
	 * @param includeStarting Whether to include the starting element.
	 * @throws IllegalArgumentException if the specified element is not part of the hierarchy.
	 */
	public static <E> void visitDepthFirst(Hierarchy<E> hierarchy, HierarchyVisitor<? super E> visitor,
			@Nullable E element, boolean includeStarting) {
		if (element == null) {
			visitDepthFirst(hierarchy, visitor);
		} else {
			check(hierarchy);
			check(visitor);
			checkArgument(hierarchy.contains(element), "The element is not part of the hierarchy");
			if (includeStarting) {
				visitor.visit(element, hierarchy.getParent(element), hierarchy.getIndex(element));
			}
			visitDepthFirst(hierarchy, element, hierarchy.getChildren(element), visitor);
		}
	}

	/**
	 * Internal traversal method.
	 * @param hierarchy Hierarchy to visit.
	 * @param parent Current parent.
	 * @param elements Children of the current parent.
	 * @param visitor Visitor.
	 */
	private static <E> void visitDepthFirst(Hierarchy<E> hierarchy, E parent, Iterable<E> elements,
			HierarchyVisitor<? super E> visitor) {
		int i = 0;
		for (final E element : elements) {
			visitor.visit(element, parent, i++);
			visitDepthFirst(hierarchy, element, hierarchy.getChildren(element), visitor);
		}
	}
}
