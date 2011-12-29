/*
 * Copyright (C) the original author or authors.
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
package net.derquinse.common.meta;

import javax.annotation.Nullable;

import net.derquinse.common.base.MorePredicates;

import com.google.common.base.Predicate;
import com.google.common.collect.Ordering;

/**
 * Base class for property descriptors of comparable types.
 * @author Andres Rodriguez
 * @param <C> Containing type.
 * @param <T> Property type.
 */
public abstract class ComparableMetaProperty<C, T extends Comparable<T>> extends MetaProperty<C, T> {
	/** Descriptor for required flag. */
	public static final MetaFlag<ComparableMetaProperty<?, ?>> REQUIRED = new MetaFlag<ComparableMetaProperty<?, ?>>(
			"required") {
		public boolean apply(ComparableMetaProperty<?, ?> input) {
			return input.isRequired();
		}
	};

	/**
	 * Default constructor.
	 * @param name Property name.
	 * @param required True if the property is required.
	 * @param validity Validity predicate.
	 * @param defaultValue Default value for the property.
	 */
	protected ComparableMetaProperty(String name, boolean required, @Nullable Predicate<? super T> validity,
			@Nullable T defaultValue) {
		super(name, required, validity, defaultValue);
	}

	/**
	 * Constructor.
	 * @param name Property name.
	 * @param required True if the property is required.
	 * @param validity Validity predicate.
	 */
	protected ComparableMetaProperty(String name, boolean required, @Nullable Predicate<? super T> validity) {
		super(name, required, validity);
	}

	/**
	 * Constructor.
	 * @param name Property name.
	 * @param required True if the property is required.
	 */
	protected ComparableMetaProperty(String name, boolean required) {
		super(name, required);
	}

	/** Returns the natural ordering on the property value. */
	public final Ordering<C> ordering() {
		return Ordering.natural().onResultOf(this);
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the property value is greater than the
	 * given value. The returned predicate does not allow null inputs.
	 */
	public final Predicate<C> greaterThan(T value) {
		return compose(MorePredicates.greaterThan(value));
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the property value is less than the given
	 * value. The returned predicate does not allow null inputs.
	 */
	public final Predicate<C> lessThan(T value) {
		return compose(MorePredicates.lessThan(value));
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the property value is greater than or
	 * equal to the given value. The returned predicate does not allow null inputs.
	 */
	public final Predicate<C> greaterOrEqual(T value) {
		return compose(MorePredicates.greaterOrEqual(value));
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the property value is less than or equal
	 * to the given value. The returned predicate does not allow null inputs.
	 */
	public final Predicate<C> lessOrEqual(T value) {
		return compose(MorePredicates.lessOrEqual(value));
	}

}
