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
package net.derquinse.common.property;

import java.util.Iterator;

/**
 * Declaration interface for properties assignable to Iterable.
 * @author Andres Rodriguez
 * @param <E> Enclosing type.
 * @param <T> Property type.
 * @param <V> Value type.
 */
public interface IterableBaseProperty<E, T extends Iterable<V>, V> extends ObjectProperty<E, T> {
	/**
	 * Returns an iterator for the property value.
	 * @param from Enclosing object.
	 * @return The requested iterator value.
	 * @throws NullPointerException if the argument is {@code null}
	 */
	Iterator<V> iterator(E from);
}
