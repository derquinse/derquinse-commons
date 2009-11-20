/*
 * Copyright 2009 the original author or authors.
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
 * Class for a 1-element tuple.
 * @author Andres Rodriguez
 * @param <T0> First element type.
 */
public abstract class Tuple1<T0> extends Tuple {
	/** Constructor. */
	Tuple1() {
	}

	/**
	 * Returns the first element.
	 * @return The first element.
	 */
	public abstract T0 get0();

	/**
	 * Creates a new tuple with the same members as this except the one provided.
	 * @param index The member to replace index.
	 * @param value Value of the member to replace.
	 * @return The new tuple.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 * @throws ClassCastException if the provided member is of an invalid type.
	 */
	public abstract Tuple1<T0> set(int index, Object value);

	/**
	 * Creates a new tuple with the same members as this except the first one.
	 * @param value Value of the first member.
	 * @return The new tuple.
	 */
	public abstract Tuple1<T0> set0(T0 value);

	/**
	 * Creates a new tuple with the same members as this except the first one, which is removed.
	 * @return The new tuple.
	 */
	public abstract Tuple curry0();

}
