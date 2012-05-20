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

import com.google.common.collect.ForwardingObject;

/**
 * A hierarchy that forward its method calls to a delegated hierarchy.
 * @author Andres Rodriguez
 * @param <E> Type of the elements in the hierarchy.
 */
public abstract class ForwardingHierarchy<E> extends ForwardingObject implements Hierarchy<E> {
	public ForwardingHierarchy() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.common.collect.ForwardingObject#delegate()
	 */
	@Override
	protected abstract Hierarchy<E> delegate();

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return delegate().isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#size()
	 */
	@Override
	public int size() {
		return delegate().size();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(E element) {
		return delegate().contains(element);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#containsAll(java.lang.Iterable)
	 */
	@Override
	public boolean containsAll(Iterable<? extends E> elements) {
		return delegate().containsAll(elements);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#elementSet()()
	 */
	public Set<E> elementSet() {
		return delegate().elementSet();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getChildren(java.lang.Object)
	 */
	public List<E> getChildren(E element) {
		return delegate().getChildren(element);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getFirstLevel()
	 */
	public List<E> getFirstLevel() {
		return delegate().getFirstLevel();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getParent(java.lang.Object)
	 */
	public E getParent(E element) {
		return delegate().getParent(element);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getSiblings(java.lang.Object)
	 */
	public List<E> getSiblings(E element) {
		return delegate().getSiblings(element);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getIndex(java.lang.Object)
	 */
	public int getIndex(E element) {
		return delegate().getIndex(element);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getRoot()
	 */
	public E getRoot() {
		return delegate().getRoot();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#isRooted()
	 */
	public boolean isRooted() {
		return delegate().isRooted();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getAncestors(java.lang.Object)
	 */
	public Iterable<E> getAncestors(E element) {
		return delegate().getAncestors(element);
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getDescendants(java.lang.Object)
	 */
	@Override
	public Set<E> getDescendants(@Nullable E element) {
		return delegate().getDescendants(element);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(@Nullable Object object) {
		return object == this || delegate().equals(object);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return delegate().hashCode();
	}

}
