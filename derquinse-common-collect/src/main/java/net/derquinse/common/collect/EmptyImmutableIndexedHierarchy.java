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

import com.google.common.collect.ImmutableBiMap;

/**
 * An empty immutable indexed hierarchy.
 * @author Andres Rodriguez
 * @param <K> Type of the keys.
 * @param <V> Type of the values.
 */
final class EmptyImmutableIndexedHierarchy extends ImmutableIndexedHierarchy<Object, Object> {
	static final EmptyImmutableIndexedHierarchy INSTANCE = new EmptyImmutableIndexedHierarchy();

	private EmptyImmutableIndexedHierarchy() {
	}

	@Override
	protected Hierarchy<Object> delegate() {
		return ImmutableHierarchy.of();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.ImmutableIndexedHierarchy#asMap()
	 */
	public ImmutableBiMap<Object, Object> asMap() {
		return ImmutableBiMap.of();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.collect.IndexedHierarchy#inverse()
	 */
	public IndexedHierarchy<Object, Object> inverse() {
		return this;
	}
}
