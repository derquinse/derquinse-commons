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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.collect.UnmodifiableIterator;

/**
 * An immutable and thread-safe implementation of hierarchy.
 * @author Andres Rodriguez
 * @param <E> Type of the elements in the hierarchy.
 */
public abstract class AbstractHierarchy<E> implements Hierarchy<E> {

	static <E> E checkRequired(E element) {
		return checkNotNull(element, "Null elements not allowed");
	}

	static void checkVisitor(HierarchyVisitor<?> visitor) {
		checkNotNull(visitor, "The visitor is required");
	}

	/** Default constructor. */
	AbstractHierarchy() {
	}

	final E checkMember(E element) {
		checkArgument(elements().contains(checkRequired(element)),
				"The provided element [%s] is not part of the hierarchy", element);
		return element;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#isEmpty()
	 */
	public boolean isEmpty() {
		return elements().isEmpty();
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#size()
	 */
	public int size() {
		return elements().size();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getCoordinates(java.lang.Object)
	 */
	public HierarchyCoordinates<E> getCoordinates(E element) {
		final E parent = getParent(element);
		final List<E> list = parent != null ? getChildren(parent) : getFirstLevel();
		return HierarchyCoordinates.of(parent, list.indexOf(element));
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getRoot()
	 */
	public E getRoot() {
		final List<E> firstLevel = getFirstLevel();
		checkArgument(firstLevel.size() == 1, "The hierarchy is not rooted");
		return firstLevel.get(0);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#isRooted()
	 */
	public boolean isRooted() {
		return getFirstLevel().size() == 1;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getAncestors(java.lang.Object)
	 */
	public Iterable<E> getAncestors(E element) {
		return new AncestorsIterable(checkMember(element));
	}

	/*
	 * (non-Javadoc)
	 * @seenet.derquinse.common.collect.Hierarchy#visitDepthFirst(net.derquinse.common.collect.
	 * HierarchyVisitor)
	 */
	public void visitDepthFirst(HierarchyVisitor<? super E> visitor) {
		checkVisitor(visitor);
		visitDepthFirst(null, getFirstLevel(), visitor);
	}

	/*
	 * (non-Javadoc)
	 * @seenet.derquinse.common.collect.Hierarchy#visitDepthFirst(net.derquinse.common.collect.
	 * HierarchyVisitor, java.lang.Object, boolean)
	 */
	public void visitDepthFirst(HierarchyVisitor<? super E> visitor, E element, boolean includeStarting) {
		if (element == null) {
			visitDepthFirst(visitor);
			return;
		}
		checkVisitor(visitor);
		if (includeStarting) {
			HierarchyCoordinates<E> c = getCoordinates(element);
			visitor.visit(element, c.getParent(), c.getIndex());
		}
		visitDepthFirst(element, getChildren(element), visitor);
	}

	/**
	 * Internal traversal method.
	 * @param parent Current parent.
	 * @param elements Children of the current parent.
	 * @param visitor Visitor.
	 */
	private void visitDepthFirst(E parent, Iterable<E> elements, HierarchyVisitor<? super E> visitor) {
		int i = 0;
		for (final E element : elements) {
			visitor.visit(element, parent, i++);
			visitDepthFirst(element, getChildren(element), visitor);
		}

	}

	final class AncestorsIterator extends UnmodifiableIterator<E> {
		/** Current element. */
		private E current;

		/**
		 * Creates a new iterator.
		 * @param start Starting element.
		 */
		AncestorsIterator(E start) {
			this.current = start;
		}

		public boolean hasNext() {
			return getParent(current) != null;
		}

		public E next() {
			E parent = getParent(current);
			if (parent == null) {
				throw new NoSuchElementException("No more ancestors.");
			}
			current = parent;
			return parent;
		}
	}

	private final class AncestorsIterable implements Iterable<E> {
		/** Starting element. */
		private final E element;

		/**
		 * Creates a new iterable.
		 * @param start Starting element.
		 */
		AncestorsIterable(E start) {
			this.element = start;
		}

		public Iterator<E> iterator() {
			return new AncestorsIterator(element);
		}
	}

}
