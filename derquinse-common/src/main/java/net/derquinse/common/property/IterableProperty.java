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

import com.google.common.base.Predicate;

/**
 * Abstract base implementation for iterable properties. The validity predicates may assume the
 * object to check is not {@code null} as nullity vs optionality checking is performed before using
 * the predicate.
 * @author Andres Rodriguez
 * @param <E> Enclosing type.
 * @param <T> Property type.
 * @param <V> Value type.
 */
public abstract class IterableProperty<E, V> extends AbstractIterableBaseProperty<E, Iterable<V>, V> {

	/**
	 * Constructor.
	 * @param name Property name.
	 * @param immutable Whether the property is immutable.
	 * @param optional Whether the property is optional.
	 * @param predicate Validity predicate.
	 */
	protected IterableProperty(String name, boolean immutable, boolean optional, Predicate<? super Iterable<V>> predicate) {
		super(name, immutable, optional, predicate);
	}

	/**
	 * Constructor.
	 * @param name Property name.
	 * @param immutable Whether the property is immutable.
	 * @param optional Whether the property is optional.
	 */
	protected IterableProperty(String name, boolean immutable, boolean optional) {
		super(name, immutable, optional);
	}
}
