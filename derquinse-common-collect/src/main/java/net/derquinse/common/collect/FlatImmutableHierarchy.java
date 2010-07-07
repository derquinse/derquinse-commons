/*
 * Copyright 2008 the original author or authors.
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

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * Implementation of {@link ImmutableHierarchy} with exactly one level.
 * @author Andres Rodriguez
 * @param <E> Type of the elements in the hierarchy.
 */
final class FlatImmutableHierarchy<E> extends ImmutableHierarchy<E> {
	private final ImmutableSet<E> set;

	FlatImmutableHierarchy(Iterable<? extends E> elements) {
		set = ImmutableSet.copyOf(elements);
	}

	@Override
	public ImmutableSet<E> elements() {
		return set;
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
		return ImmutableList.of();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getFirstLevel()
	 */
	public List<E> getFirstLevel() {
		return set.asList();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getParent(java.lang.Object)
	 */
	public E getParent(E element) {
		checkMember(element);
		return null;
	}
}
