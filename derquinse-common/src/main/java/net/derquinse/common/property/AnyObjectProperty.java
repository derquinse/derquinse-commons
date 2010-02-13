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
package net.derquinse.common.property;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

/**
 * Reference type property declaration interface.
 * @author Andres Rodriguez
 * @param <E> Enclosing type.
 * @param <T> Property type.
 */
public interface AnyObjectProperty<E, T> extends Property<E>, Function<E, T> {
	/**
	 * Returns the property value.
	 * @param from Enclosing object.
	 * @return The property value.
	 * @throws NullPointerException if the argument is {@code null}
	 */
	T apply(E from);

	/**
	 * Returns whether the property is optional.
	 * @return True if the property is optional.
	 */
	boolean isOptional();

	/**
	 * Returns a predicate that evaluates to true if the property of the object reference being tested
	 * is {@code null}. If the object reference is {@code null} the predicate will throw {@code
	 * NullPointerException}.
	 * @return The requested predicate.
	 */
	Predicate<E> isNull();

	/**
	 * Returns a predicate that evaluates to true if the property of the object reference being tested
	 * is not {@code null}. If the object reference is {@code null} the predicate will throw {@code
	 * NullPointerException}.
	 * @return The requested predicate.
	 */
	Predicate<E> notNull();

	/**
	 * Returns a predicate that evaluates to true if the object being tested and the target object are
	 * {@code null}, if the property value of the object {@code equals()} the given target's or both
	 * values are null.
	 * @param target Target object.
	 * @return The requested predicate.
	 */
	Predicate<E> equalTo(E target);

	/**
	 * Returns the property validity predicate.
	 * @return The requested predicate.
	 */
	Predicate<T> getPredicate();

	/**
	 * Checks whether the provided value is valid according to the property's validity predicate.
	 * Equivalent to {@code getPredicate().apply(value)}
	 * @param value The value to check.
	 * @return True if the value is valid.
	 */
	boolean isValid(T value);

	/**
	 * Checks whether the provided value is valid according to the property's validity predicate.
	 * Equivalent to {@code Preconditions.checkArgument(isValid(value))}
	 * @param value The value to check.
	 * @return The provided value, which is valid.
	 * @throws NullPointerException if the property required and the argument is {@code null}.
	 * @throws IllegalArgumentException if the provided value is invalid.
	 */
	T check(T value);

}
