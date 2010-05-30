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

import com.google.common.collect.ForwardingObject;

/**
 * A hierarchy that forward its method calls to a delegated hierarchy.
 * @author Andres Rodriguez
 * @param <E> Type of the elements in the hierarchy.
 */
public abstract class ForwardingHierarchy<E> extends ForwardingObject implements Hierarchy<E> {
	public ForwardingHierarchy() {
	}

	@Override
	protected abstract Hierarchy<E> delegate();

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#elements()
	 */
	public Set<E> elements() {
		return delegate().elements();
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
	 * @see net.derquinse.common.collect.Hierarchy#getCoordinates(java.lang.Object)
	 */
	public HierarchyCoordinates<E> getCoordinates(E element) {
		return delegate().getCoordinates(element);
	};

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
	 * @seenet.derquinse.common.collect.Hierarchy#visitDepthFirst(net.derquinse.common.collect.
	 * HierarchyVisitor)
	 */
	public void visitDepthFirst(HierarchyVisitor<? super E> visitor) {
		delegate().visitDepthFirst(visitor);
	}

	/*
	 * (non-Javadoc)
	 * @seenet.derquinse.common.collect.Hierarchy#visitDepthFirst(net.derquinse.common.collect.
	 * HierarchyVisitor, java.lang.Object, boolean)
	 */
	public void visitDepthFirst(net.derquinse.common.collect.HierarchyVisitor<? super E> visitor, E element,
			boolean includeStarting) {
		delegate().visitDepthFirst(visitor, element, includeStarting);
	};
}
