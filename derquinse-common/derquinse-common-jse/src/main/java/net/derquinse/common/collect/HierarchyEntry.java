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
import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nullable;

import com.google.common.base.Objects;

/**
 * A hierarchy entry.
 * @author Andres Rodriguez
 * @param <E> Type of the elements in the hierarchy.
 */
public final class HierarchyEntry<E> {
	public static <E> HierarchyEntry<E> of(HierarchyCoordinates<E> coordinates, E element) {
		return new HierarchyEntry<E>(coordinates, element);
	}

	public static <E> HierarchyEntry<E> of(@Nullable E parent, int index, E element) {
		return new HierarchyEntry<E>(HierarchyCoordinates.of(parent, index), element);
	}

	/** Coordinates. */
	private final HierarchyCoordinates<E> coordinates;
	/** Element. */
	private final E element;

	private HierarchyEntry(HierarchyCoordinates<E> coordinates, E element) {
		this.coordinates = checkNotNull(coordinates, "The coordinates are required");
		this.element = checkNotNull(element, "Null elements not allowed in hierarchies");
	}

	public HierarchyCoordinates<E> getCoordinates() {
		return coordinates;
	}

	public E getElement() {
		return element;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HierarchyEntry<?>) {
			HierarchyEntry<?> other = (HierarchyEntry<?>) obj;
			return equal(element, other.element) && equal(coordinates, other.coordinates);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(element, coordinates);
	}

	@Override
	public String toString() {
		return String.format("[[%s] at %s]", element, coordinates);
	}
}
