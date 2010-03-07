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
package net.derquinse.common.product;

import java.util.Iterator;

/**
 * Base class for cartesian powers.
 * @author Andres Rodriguez
 * @param <T> Element type.
 */
public abstract class Power<T> extends AbstractProduct implements Iterable<T> {
	/** Default constructor. */
	Power() {
	}
	
	/**
	 * Returns the element at position index (0 <= index < arity).
	 * @param index The requested index.
	 * @return The requested element.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	public abstract T get(int index);
	
	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Product#asIterable()
	 */
	@Override
	public final Iterable<? extends Object> asIterable() {
		return this;
	}
	
	@Override
	public Iterator<? extends Object> productIterator() {
		return iterator();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<T> iterator() {
		return new PowerIterator<T>(this);
	}

	/**
	 * Creates a new power with the same elements as this except the one provided.
	 * @param index The element to replace index.
	 * @param value Value of the element to replace.
	 * @return The new power.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	public abstract Power<T> set(int index, T value);

	/**
	 * Creates a new power with the same elements as this except the one with the provided index, which
	 * is removed.
	 * @param index The member to remove index.
	 * @return The new power.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	public abstract Power<T> curry(int index);
}
