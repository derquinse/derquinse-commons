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
import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * An immutable and thread-safe implementation of hierarchy.
 * @author Andres Rodriguez
 * @param <E> Type of the elements in the hierarchy.
 */
public abstract class ImmutableHierarchy<E> extends AbstractHierarchy<E> {

	/**
	 * Returns an empty immutable hierarchy.
	 */
	@SuppressWarnings("unchecked")
	public static <E> ImmutableHierarchy<E> of() {
		return (ImmutableHierarchy<E>) EmptyImmutableHierarchy.INSTANCE;
	}

	/**
	 * Returns an immutable copy of the provided hierarchy. If the argument is itself an immutable
	 * hierarchy no copy is performed.
	 * @param hierarchy Hierarchy to copy.
	 * @return An immutable hierarchy equal to the the one provided.
	 */
	public static <E> ImmutableHierarchy<E> copyOf(Hierarchy<? extends E> hierarchy) {
		checkNotNull(hierarchy, "The source hierachy must be provided");
		if (hierarchy instanceof ImmutableHierarchy<?>) {
			@SuppressWarnings("unchecked")
			final ImmutableHierarchy<E> h = (ImmutableHierarchy<E>) hierarchy;
			return h;
		} else if (hierarchy.isEmpty()) {
			return of();
		}
		final Builder<E> builder = builder();
		return builder.addHierarchy(null, hierarchy, null, true).get();
	}

	/**
	 * Returns a new builder that does not allow out of order insertion (initially).
	 */
	public static <E> Builder<E> builder() {
		return new Builder<E>();
	}

	/**
	 * Returns a new builder.
	 * @param outOfOrder Whther to allow out of order insertion.
	 */
	public static <E> Builder<E> builder(boolean outOfOrder) {
		return new Builder<E>().setAllowOutOfOrder(outOfOrder);
	}

	/** Default constructor. */
	ImmutableHierarchy() {
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#elements()
	 */
	public abstract ImmutableSet<E> elements();

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public final boolean add(E e) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public final boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#clear()
	 */
	public final void clear() {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public final boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	public final boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	public final boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Builder for immutable hierarchies.
	 * @author Andres Rodriguez
	 * @param <E> Type of the elements.
	 */
	public static final class Builder<E> implements Supplier<ImmutableHierarchy<E>> {
		private final Set<E> elements = Sets.newHashSet();
		private final Set<E> unaddedParents = Sets.newHashSet();
		private final List<E> firstLevel = Lists.newLinkedList();
		private final Map<E, E> parents = Maps.newHashMap();
		private final ListMultimap<E, E> children = LinkedListMultimap.create();
		private boolean allowOutOfOrder;

		/** Constructor. Use static factory method. */
		private Builder(boolean allowOutOfOrder) {
			this.allowOutOfOrder = allowOutOfOrder;
		}

		private Builder() {
			this(false);
		}

		private void checkReady() {
			checkState(unaddedParents.isEmpty(), "There are referenced parents that have not been added yet.");
		}

		Set<E> getElements() {
			checkReady();
			return elements;
		}

		List<E> getFirstLevel() {
			checkReady();
			return firstLevel;
		}

		Map<E, E> getParents() {
			return parents;
		}

		ListMultimap<E, E> getChildren() {
			checkReady();
			return children;
		}

		public boolean isAllowOutOfOrder() {
			return allowOutOfOrder;
		}

		public Builder<E> setAllowOutOfOrder(boolean allowOutOfOrder) {
			this.allowOutOfOrder = allowOutOfOrder;
			return this;
		}

		public Builder<E> add(E parent, E element) {
			checkArgument(!elements.contains(element), "Duplicate entries not allowed in a hierarchy");
			if (parent == null) {
				firstLevel.add(element);
			} else {
				if (!allowOutOfOrder) {
					checkArgument(elements.contains(parent), "Parent not found in the hierarchy");
				}
				E p = parent;
				@SuppressWarnings("unchecked")
				final Set<E> visited = Sets.newHashSet(element);
				while (p != null) {
					checkState(visited.add(p), "Loop detected: element [%s] visited twice", p);
					p = parents.get(p);
				}
				children.put(parent, element);
				parents.put(element, parent);
				if (!elements.contains(parent)) {
					unaddedParents.add(parent);
				}
			}
			unaddedParents.remove(element);
			elements.add(element);
			return this;
		}

		public Builder<E> addAll(E parent, Iterable<? extends E> elements) {
			for (E element : elements) {
				add(parent, element);
			}
			return this;
		}

		public Builder<E> addAll(E parent, E... elements) {
			for (E element : elements) {
				add(parent, element);
			}
			return this;
		}

		private static <T> Hierarchy<T> check(Hierarchy<? extends T> hierarchy) {
			checkNotNull(hierarchy, "The source hierarchy is required");
			// Safe because we are just reading.
			@SuppressWarnings("unchecked")
			Hierarchy<T> h = (Hierarchy<T>) hierarchy;
			return h;
		}

		public Builder<E> addHierarchy(E parent, Hierarchy<? extends E> hierarchy, @Nullable E root, boolean includeRoot) {
			final Hierarchy<E> h = check(hierarchy);
			checkArgument(root == null || hierarchy.elements().contains(root));
			List<E> level;
			if (root != null) {
				if (includeRoot) {
					add(parent, root);
					parent = root;
				}
				level = h.getChildren(root);
			} else {
				level = h.getFirstLevel();
			}
			addHierarchyRec(parent, h, level);
			return this;
		}

		private void addHierarchyRec(E parent, Hierarchy<E> hierarchy, List<E> level) {
			for (E element : level) {
				add(parent, element);
				addHierarchyRec(element, hierarchy, hierarchy.getChildren(element));
			}
		}

		public <F> Builder<E> addHierarchy(E parent, Hierarchy<? extends F> hierarchy, @Nullable F root,
				boolean includeRoot, Function<? super F, E> function) {
			final Hierarchy<F> h = check(hierarchy);
			checkNotNull(hierarchy, "The transformation function is required");
			checkArgument(root == null || hierarchy.elements().contains(root));
			List<F> level;
			if (root != null) {
				if (includeRoot) {
					E newRoot = function.apply(root);
					add(parent, newRoot);
					parent = newRoot;
				}
				level = h.getChildren(root);
			} else {
				level = h.getFirstLevel();
			}
			addHierarchyRec(parent, h, level, function);
			return this;
		}

		private <F> void addHierarchyRec(E parent, Hierarchy<F> hierarchy, List<F> level, Function<? super F, E> function) {
			for (F element : level) {
				E transformed = function.apply(element);
				add(parent, transformed);
				addHierarchyRec(transformed, hierarchy, hierarchy.getChildren(element), function);
			}
		}

		/**
		 * Builds and returns an immutable hierarchy with the nodes added up to the method call.
		 * @returns An immutable hierarchy.
		 * @throws IllegalStateException if there are referenced parents that are not part of the
		 *           hierarchy yet.
		 */
		public ImmutableHierarchy<E> get() {
			checkReady();
			if (elements.isEmpty()) {
				return of();
			} else if (children.isEmpty()) {
				return new FlatImmutableHierarchy<E>(firstLevel);
			}
			return new RegularImmutableHierarchy<E>(this);
		}

	}
}
