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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Predicates.alwaysTrue;
import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.or;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * Abstract implementation for properties. The validity predicates may assume the object to check is
 * not {@code null} as nullity vs optionality checking is performed before using the predicate.
 * @author Andres Rodriguez
 * @param <E> Enclosing type.
 * @param <T> Property type.
 */
public abstract class ObjectProperty<E, T> extends AbstractPropertyMember<E> implements Property<E, T> {
	/** Whether the property is optional. */
	private final boolean optional;
	/** Validity predicate. */
	private final Predicate<T> predicate;

	/**
	 * Constructor.
	 * @param name Property name.
	 * @param immutable Whether the property is immutable.
	 * @param optional Whether the property is optional.
	 * @param predicate Validity predicate.
	 */
	ObjectProperty(String name, boolean immutable, boolean optional, Predicate<? super T> predicate) {
		super(name, immutable);
		this.optional = optional;
		if (optional) {
			if (predicate == null) {
				this.predicate = alwaysTrue();
			} else {
				this.predicate = or(Predicates.isNull(), predicate);
			}
		} else {
			if (predicate == null) {
				this.predicate = Predicates.notNull();
			} else {
				this.predicate = and(Predicates.notNull(), predicate);
			}
		}
	}

	/**
	 * Constructor.
	 * @param name Property name.
	 * @param immutable Whether the property is immutable.
	 * @param optional Whether the property is optional.
	 */
	ObjectProperty(String name, boolean immutable, boolean optional) {
		this(name, optional, immutable, null);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.AnyObjectProperty#isOptional()
	 */
	public final boolean isOptional() {
		return optional;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.AnyObjectProperty#isNull()
	 */
	public final Predicate<E> isNull() {
		return Predicates.compose(Predicates.isNull(), this);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.AnyObjectProperty#notNull()
	 */
	public final Predicate<E> notNull() {
		return Predicates.compose(Predicates.notNull(), this);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.AnyObjectProperty#equalTo(java.lang.Object)
	 */
	public final Predicate<E> equalTo(final E target) {
		if (target == null) {
			return Predicates.isNull();
		}
		return new Predicate<E>() {
			public boolean apply(E input) {
				return input != null && Objects.equal(apply(target), apply(input));
			};
		};
	};

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.AnyObjectProperty#getPredicate()
	 */
	public final Predicate<T> getPredicate() {
		return predicate;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.AnyObjectProperty#isValid(java.lang.Object)
	 */
	public final boolean isValid(T value) {
		return predicate.apply(value);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.AnyObjectProperty#check(java.lang.Object)
	 */
	public final T check(T value) {
		if (!optional) {
			checkNotNull(value, "The property %s is required", getName());
		}
		checkArgument(predicate.apply(value), "Invalid value for property %s", getName());
		return value;
	}

}
