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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * An empty immutable hierarchy.
 * @author Andres Rodriguez
 */
final class EmptyImmutableHierarchy extends ImmutableHierarchy<Object> {
	static final EmptyImmutableHierarchy INSTANCE = new EmptyImmutableHierarchy();

	private EmptyImmutableHierarchy() {
	}

	@Override
	public ImmutableSet<Object> elements() {
		return ImmutableSet.of();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getChildren(java.lang.Object)
	 */
	public List<Object> getChildren(Object element) {
		checkMember(element);
		return ImmutableList.of();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getFirstLevel()
	 */
	public List<Object> getFirstLevel() {
		return ImmutableList.of();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		checkMember(element);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#getRoot()
	 */
	public Object getRoot() {
		throw new IllegalStateException("An empty hierarchy has no root");
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.Hierarchy#isRooted()
	 */
	public boolean isRooted() {
		return false;
	}
}
