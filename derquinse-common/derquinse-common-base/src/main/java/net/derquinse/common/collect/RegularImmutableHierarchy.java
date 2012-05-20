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

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.MapMaker;

/**
 * Implementation of {@link ImmutableHierarchy} with two or more levels.
 * @author Andres Rodriguez
 * @param <E> Type of the elements in the hierarchy.
 */
final class RegularImmutableHierarchy<E> extends ImmutableHierarchy<E> {
	private transient final ImmutableSet<E> elements;
	private final ImmutableList<E> firstLevel;
	private transient final ImmutableMap<E, E> parents;
	private final ImmutableListMultimap<E, E> children;
	/** Computed descendants. */
	private transient final ConcurrentMap<E, ImmutableSet<E>> descendants;

	RegularImmutableHierarchy(ImmutableHierarchy.Builder<E> builder) {
		this.elements = ImmutableSet.copyOf(builder.getElements());
		this.firstLevel = ImmutableList.copyOf(builder.getFirstLevel());
		this.parents = ImmutableMap.copyOf(builder.getParents());
		this.children = ImmutableListMultimap.copyOf(builder.getChildren());
		// Incomplete integrity check
		checkArgument(elements.size() == firstLevel.size() + children.size(), "Inconsistent hierarchy.");
		this.descendants = new MapMaker().initialCapacity(this.elements.size()).makeMap();
	}

	@Override
	public ImmutableSet<E> elementSet() {
		return elements;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getChildren(java.lang.Object)
	 */
	public List<E> getChildren(@Nullable E element) {
		if (element == null) {
			return getFirstLevel();
		}
		checkMember(element);
		return children.get(element);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getFirstLevel()
	 */
	public List<E> getFirstLevel() {
		return firstLevel;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getParent(java.lang.Object)
	 */
	public E getParent(E element) {
		checkMember(element);
		return parents.get(element);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.ImmutableHierarchy#getDescendants(java.lang.Object)
	 */
	public ImmutableSet<E> getDescendants(E element) {
		checkMember(element);
		ImmutableSet<E> set = descendants.get(element);
		if (set != null) {
			return set;
		}
		ImmutableSet.Builder<E> b = ImmutableSet.builder();
		for (E e : getChildren(element)) {
			b.add(e);
			b.addAll(getDescendants(e));
		}
		set = b.build();
		descendants.put(element, set);
		return set;
	}

}
