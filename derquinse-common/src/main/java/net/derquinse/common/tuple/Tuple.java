/*
 * Copyright 2008-2009 the original author or authors.
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
package net.derquinse.common.tuple;

/**
 * Base interface for tuples. For tuples to obey {@code equals} and {@code
 * hashCode} contracts, it is strongly recommended to use only immutable objects
 * as tuple members. Moreover, implementation are free to pre-compute and cache
 * the tuple's hash code at construction time.
 * @author Andres Rodriguez
 */
public interface Tuple extends Iterable<Object> {
	/**
	 * Returns the tuple's arity.
	 * @return The tuple's arity (>0).
	 */
	int arity();

	/**
	 * Returns the element at position index (0 <= index < arity).
	 * @param index The requested index.
	 * @return The requested element.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	Object get(int index);

	/**
	 * Creates a new tuple with the same members as this except the one provided.
	 * @param index The member to replace index.
	 * @param value Value of the member to replace.
	 * @return The new tuple.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 * @throws ClassCastException if the provided member is of an invalid type.
	 */
	Tuple set(int index);

}
