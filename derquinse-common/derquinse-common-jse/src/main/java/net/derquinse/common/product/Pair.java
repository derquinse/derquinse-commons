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
 * Interface for a 1-element cartesian power (pair).
 * @author Andres Rodriguez
 * @param <T> Element type.
 */
public interface Pair<T> extends Singleton<T>, Tuple2<T, T> {
	/**
	 * Creates a new power with the same elements as this except the one with the provided index,
	 * which is removed.
	 * @param index The member to remove index.
	 * @return The new power.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	Singleton<T> curry(int index);

	/**
	 * Creates a new tuple with the same members as this except the first one, which is removed.
	 * @return The new tuple.
	 */
	Singleton<T> curry0();

	/**
	 * Creates a new tuple with the same members as this except the second one, which is removed.
	 * @return The new tuple.
	 */
	Singleton<T> curry1();

}
