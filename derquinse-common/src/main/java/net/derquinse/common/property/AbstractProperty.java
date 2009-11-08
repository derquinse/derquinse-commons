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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * Abstract implementation for properties. The validity predicates may assume
 * the object to check is not {@code null} as nullity vs optionality checking is
 * performed before using the predicate.
 * @author Andres Rodriguez
 * @param <E> Enclosing type.
 * @param <T> Property type.
 */
public abstract class AbstractProperty<E, T> implements Property<E, T> {
	/** Property name. */
	private final String name;
	/** Whether the property is optional. */
	private final boolean optional;
	/** Whether the property is immutable. */
	private final boolean immutable;
	/** Validity predicate. */
	private final Predicate<T> predicate;

	/**
	 * Constructor.
	 * @param name Property name.
	 * @param optional Whether the property is optional.
	 * @param immutable Whether the property is immutable.
	 * @param predicate Validity predicate.
	 */
	AbstractProperty(String name, boolean optional, boolean immutable, Predicate<? super T> predicate) {
		this.name = checkNotNull(name, "The property name must be provided.");
		this.optional = optional;
		this.immutable = immutable;
		if (optional) {
			if (predicate == null) {
				this.predicate = Predicates.alwaysTrue();
			} else {
				this.predicate = Predicates.or(Predicates.isNull(), predicate);
			}
		} else {
			if (predicate == null) {
				this.predicate = Predicates.notNull();
			} else {
				this.predicate = Predicates.and(Predicates.notNull(), predicate);
			}
		}
	}

	/**
	 * Constructor.
	 * @param name Property name.
	 * @param optional Whether the property is optional.
	 * @param immutable Whether the property is immutable.
	 */
	AbstractProperty(String name, boolean optional, boolean immutable) {
		this(name, optional, immutable, null);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.Property#getName()
	 */
	@Override
	public final String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.Property#isOptional()
	 */
	@Override
	public final boolean isOptional() {
		return optional;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.Property#isImmutable()
	 */
	@Override
	public final boolean isImmutable() {
		return immutable;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.Property#isNull()
	 */
	@Override
	public final Predicate<E> isNull() {
		return Predicates.compose(Predicates.isNull(), this);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.Property#notNull()
	 */
	@Override
	public final Predicate<E> notNull() {
		return Predicates.compose(Predicates.notNull(), this);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.Property#equalTo(java.lang.Object)
	 */
	@Override
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
	 * @see net.derquinse.common.property.Property#getPredicate()
	 */
	@Override
	public final Predicate<T> getPredicate() {
		return predicate;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.Property#isValid(java.lang.Object)
	 */
	@Override
	public final boolean isValid(T value) {
		return predicate.apply(value);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.Property#check(java.lang.Object)
	 */
	@Override
	public final T check(T value) {
		if (!optional) {
			checkNotNull(value, "The property %s is required", name);
		}
		checkArgument(predicate.apply(value), "Invalid value for property %s", name);
		return value;
	}

}
