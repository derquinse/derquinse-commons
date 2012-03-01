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
import static com.google.common.collect.Iterables.transform;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
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

	/** Default constructor. */
	AbstractHierarchy() {
	}

	final E checkMember(E element) {
		checkArgument(elementSet().contains(checkRequired(element)),
				"The provided element [%s] is not part of the hierarchy", element);
		return element;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#isEmpty()
	 */
	public boolean isEmpty() {
		return elementSet().isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#size()
	 */
	public int size() {
		return elementSet().size();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#contains(java.lang.Object)
	 */
	public boolean contains(E element) {
		return elementSet().contains(element);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#containsAll(java.lang.Iterable)
	 */
	@Override
	public boolean containsAll(Iterable<? extends E> elements) {
		Collection<?> collection = (elements instanceof Collection) ? ((Collection<?>) elements) : Sets
				.newHashSet(elements);
		return elementSet().containsAll(collection);
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
	 * @see net.derquinse.common.collect.Hierarchy#getSiblings(java.lang.Object)
	 */
	public List<E> getSiblings(E element) {
		final E parent = getParent(element);
		return parent == null ? getFirstLevel() : getChildren(parent);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getIndex(java.lang.Object)
	 */
	public int getIndex(E element) {
		return getSiblings(element).indexOf(element);
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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int h = 0;
		for (E element : getFirstLevel()) {
			h += hashCode(0, element);
		}
		return h;
	}

	private int hashCode(int level, E element) {
		int h = element.hashCode() ^ level;
		final int next = level + 1;
		for (E child : getChildren(element)) {
			h += hashCode(next, child);
		}
		return h;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Hierarchy<?>) {
			// SAFE: Read-only.
			@SuppressWarnings("unchecked")
			final Hierarchy<Object> h = (Hierarchy<Object>) obj;
			if (isRooted() == h.isRooted() && size() == h.size()) {
				return equals(null, h);
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final Joiner j = Joiner.on(", ");
		final Function<E, String> f = new Function<E, String>() {
			public String apply(E input) {
				String element = input.toString();
				List<E> children = getChildren(input);
				if (children.isEmpty()) {
					return element;
				}
				StringBuilder b = new StringBuilder(element).append('[');
				j.appendTo(b, transform(children, this));
				return b.append(']').toString();
			}
		};
		StringBuilder b = new StringBuilder("Hierarchy[");
		j.appendTo(b, transform(getFirstLevel(), f));
		return b.append(']').toString();
	}

	/* Equality helper. */
	private boolean equals(@Nullable E element, Hierarchy<Object> other) {
		final List<E> a = getChildren(element);
		if (a.equals(other.getChildren(element))) {
			for (E e : a) {
				if (!equals(e, other)) {
					return false;
				}
			}
			return true;
		}
		return false;
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
