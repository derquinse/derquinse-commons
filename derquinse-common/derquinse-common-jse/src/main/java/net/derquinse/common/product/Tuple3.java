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
 * Interface for a 3-element tuple.
 * @author Andres Rodriguez
 * @param <T0> First element type.
 * @param <T1> Second element type.
 * @param <T2> Third element type.
 */
public interface Tuple3<T0, T1, T2> extends Tuple2<T0, T1>, Product3<T0, T1, T2> {
	/**
	 * Creates a new tuple with the same members as this except the one with the provided index, which
	 * is removed.
	 * @param index The member to remove index.
	 * @return The new tuple.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	Tuple2<?, ?> curry(int index);

	/**
	 * Creates a new tuple with the same members as this except the first one, which is removed.
	 * @return The new tuple.
	 */
	Tuple2<T1, T2> curry0();

	/**
	 * Creates a new tuple with the same members as this except the second one, which is removed.
	 * @return The new tuple.
	 */
	Tuple2<T0, T2> curry1();

	/**
	 * Creates a new tuple with the same members as this except the third one, which is removed.
	 * @return The new tuple.
	 */
	Tuple2<T0, T1> curry2();
}
