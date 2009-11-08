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

import com.google.common.base.Function;
import com.google.common.base.Predicate;

/**
 * Property declaration interface.
 * @author Andres Rodriguez
 * @param <E> Enclosing type.
 * @param <T> Property type.
 */
public interface Property<E, T> extends Function<E, T> {
	/**
	 * Returns the property value.
	 * @param from Enclosing object.
	 * @return The property value.
	 * @throws NullPointerException if the argument is {@code null}
	 */
	T apply(E from);

	/**
	 * Returns the property name.
	 * @return The property name.
	 */
	String getName();

	/**
	 * Returns whether the property is optional.
	 * @return True if the property is optional.
	 */
	boolean isOptional();

	/**
	 * Returns whether the property is immutable.
	 * @return True if the property is immutable.
	 */
	boolean isImmutable();

	/**
	 * Returns a predicate that evaluates to true if the property of the object
	 * reference being tested is {@code null}. If the object reference is
	 * {@code null} the predicate will throw {@code NullPointerException}.
	 * @return The requested predicate.
	 */
	Predicate<E> isNull();

	/**
	 * Returns a predicate that evaluates to true if the property of the object
	 * reference being tested is not {@code null}. If the object reference is
	 * {@code null} the predicate will throw {@code NullPointerException}.
	 * @return The requested predicate.
	 */
	Predicate<E> notNull();

	/**
	 * Returns a predicate that evaluates to true if the object being tested and
	 * the target object are {@code null}, if the property value of the object
	 * {@code equals()} the given target's or both values are null.
	 * @param target Target object.
	 * @return The requested predicate.
	 */
	Predicate<E> equalTo(E target);

}
