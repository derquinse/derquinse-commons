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
 * Interface for a 1-element cartesian power.
 * @author Andres Rodriguez
 * @param <T> Element type.
 */
public interface AbstractSingleton<T> extends Power<T>, Product1<T> {
	/**
	 * Creates a new singleton with the same elements as this except the one provided.
	 * @param index The element to replace index.
	 * @param value Value of the element to replace.
	 * @return The new singleton.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	AbstractSingleton<T> set(int index, T value);

	/**
	 * Creates a new singleton with the same elements as this except the first one.
	 * @param value Value of the element.
	 * @return The new singleton.
	 */
	AbstractSingleton<T> set0(T value);

	/**
	 * Creates a new tuple with the same members as this except the first one, which is removed.
	 * @return The new tuple.
	 */
	Power<T> curry0();

}
