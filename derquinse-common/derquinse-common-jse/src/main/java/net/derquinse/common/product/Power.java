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
 * Base interface for cartesian powers.
 * @author Andres Rodriguez
 * @param <T> Element type.
 */
public interface Power<T> extends Tuple, Iterable<T> {
	/**
	 * Returns the element at position index (0 <= index < arity).
	 * @param index The requested index.
	 * @return The requested element.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	T get(int index);

	/**
	 * Creates a new power with the same elements as this except the one with the provided index,
	 * which is removed.
	 * @param index The member to remove index.
	 * @return The new power.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	Power<T> curry(int index);
}
