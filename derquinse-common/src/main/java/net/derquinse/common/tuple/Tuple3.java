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
 * Class for a 3-element tuple.
 * @author Andres Rodriguez
 * @param <T0> First element type.
 * @param <T1> Second element type.
 * @param <T2> Third element type.
 */
public abstract class Tuple3<T0, T1, T2> extends Tuple2<T0, T1> {
	/**
	 * Internal constructor for subclasses.
	 * @param e Elements. No defensive copy is made.
	 */
	Tuple3(Object... e) {
		super(e);
	}

	/**
	 * Returns the third element.
	 * @return The third element.
	 */
	@SuppressWarnings("unchecked")
	public T2 get2() {
		return (T2) get(2);
	}

	/**
	 * Creates a new tuple with the same members as this except the one
	 * provided.
	 * @param index The member to replace index.
	 * @param value Value of the member to replace.
	 * @return The new tuple.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 * @throws ClassCastException if the provided member is of an invalid type.
	 */
	public abstract Tuple3<T0, T1, T2> set(int index, Object value);

	/**
	 * Creates a new tuple with the same members as this except the first one.
	 * @param value Value of the first member.
	 * @return The new tuple.
	 */
	public abstract Tuple3<T0, T1, T2> set0(T0 value);

	/**
	 * Creates a new tuple with the same members as this except the second one.
	 * @param value Value of the second member.
	 * @return The new tuple.
	 */
	public abstract Tuple3<T0, T1, T2> set1(T1 value);

	/**
	 * Creates a new tuple with the same members as this except the second one.
	 * @param value Value of the second member.
	 * @return The new tuple.
	 */
	public abstract Tuple3<T0, T1, T2> set2(T2 value);

	/**
	 * Creates a new tuple with the same members as this except the one with the
	 * provided index, which is removed.
	 * @param index The member to remove index.
	 * @return The new tuple.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	public abstract Tuple2<?, ?> curry(int index);

	/**
	 * Creates a new tuple with the same members as this except the first one,
	 * which is removed.
	 * @return The new tuple.
	 */
	public abstract Tuple2<T1, T2> curry0();

	/**
	 * Creates a new tuple with the same members as this except the second one,
	 * which is removed.
	 * @return The new tuple.
	 */
	public abstract Tuple2<T0, T2> curry1();

	/**
	 * Creates a new tuple with the same members as this except the third one,
	 * which is removed.
	 * @return The new tuple.
	 */
	public abstract Tuple2<T0, T1> curry2();
}
