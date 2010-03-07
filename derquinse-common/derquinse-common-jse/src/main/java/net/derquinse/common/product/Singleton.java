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

/**
 * Class for a 1-element power.
 * @author Andres Rodriguez
 * @param <T> Element type.
 */
public abstract class Singleton<T> extends Power<T> implements Product1<T> {
	/** Constructor. */
	Singleton() {
	}

	/**
	 * Creates a new power with the same members as this except the one provided.
	 * @param index The element to replace index.
	 * @param value Value of the element to replace.
	 * @return The new power.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 * @throws ClassCastException if the provided member is of an invalid type.
	 */
	public abstract Singleton<T> set(int index, T value);

	/**
	 * Creates a new power with the same elements as this except the one with the provided index, which
	 * is removed.
	 * @param index The member to remove index.
	 * @return The new power.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	public abstract Singleton<T> curry(int index);

	/**
	 * Creates a new tuple with the same members as this except the first one, which is removed.
	 * @return The new tuple.
	 */
	public abstract Power<T> curry0();

}
