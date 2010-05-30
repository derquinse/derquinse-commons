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

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkArgument;

import javax.annotation.Nullable;

import com.google.common.base.Objects;

/**
 * The coordinates of an element in a hierarchy.
 * @author Andres Rodriguez
 * @param <E> Type of the elements in the hierarchy.
 */
public final class HierarchyCoordinates<E> {
	public static <E> HierarchyCoordinates<E> of(@Nullable E parent, int index) {
		checkArgument(index >= 0, "Negative index");
		return new HierarchyCoordinates<E>(parent, index);
	}

	/** Parent element. */
	private final E parent;
	/** Index in parent's children list. */
	private final int index;

	private HierarchyCoordinates(@Nullable E parent, int index) {
		this.parent = parent;
		this.index = index;
	}

	/**
	 * Returns the parent element.
	 * @return The parent element or {@code null} if it is a first-level element.
	 */
	public E getParent() {
		return parent;
	}

	/**
	 * Returns the index in parent's children list.
	 * @return The index in parent's children list.
	 */
	public int getIndex() {
		return index;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HierarchyCoordinates<?>) {
			HierarchyCoordinates<?> other = (HierarchyCoordinates<?>) obj;
			return index == other.index && equal(parent, other.parent);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(parent, index);
	}

	@Override
	public String toString() {
		return String.format("[%s/%d]", parent, index);
	}
}
