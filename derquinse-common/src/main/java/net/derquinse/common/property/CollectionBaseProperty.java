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

import java.util.Collection;

import com.google.common.base.Predicate;

/**
 * Declaration interface for properties assignable to Collection.
 * @author Andres Rodriguez
 * @param <E> Enclosing type.
 * @param <T> Property type.
 * @param <V> Value type.
 */
public interface CollectionBaseProperty<E, T extends Collection<V>, V> extends IterableBaseProperty<E, T, V> {

	/**
	 * Returns a predicate that evaluates to true if the value being tested is
	 * contained in the collection specified by the value of the current
	 * property of the target object. If the target object is {@code null}, and
	 * always false predicate is returned.
	 * @param target Target object.
	 * @return The requested predicate.
	 */
	Predicate<V> in(E target);

}
