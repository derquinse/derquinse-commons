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

import java.util.Arrays;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * Base class for flag descriptors.
 * @author Andres Rodriguez
 * @param <C> Containing type.
 */
public abstract class MetaFlag<C> extends Meta<C> implements Predicate<C> {
	/** Default value. */
	private final boolean defaultValue;

	/**
	 * Default constructor.
	 * @param name Property name.
	 * @param defaultValue Default value for the flag.
	 */
	protected MetaFlag(String name, boolean defaultValue) {
		super(name);
		this.defaultValue = defaultValue;
	}

	/**
	 * Constructor with a default value of false.
	 * @param name Property name.
	 */
	protected MetaFlag(String name) {
		this(name, false);
	}

	/**
	 * Returns the default value.
	 */
	public final boolean getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Returns a view of the flag as a Boolean property.
	 */
	public final BooleanMetaProperty<C> asProperty() {
		return new FlagProperty();
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if this flag evaluates to {@code false}.
	 */
	public final Predicate<C> not() {
		return Predicates.not(this);
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if this flag and each of the provided
	 * components evaluates to {@code true}. The components are evaluated in order (after the flag),
	 * and evaluation will be "short-circuited" as soon as a false predicate is found. It defensively
	 * copies the iterable passed in, so future changes to it won't alter the behavior of this
	 * predicate.
	 */
	public final Predicate<C> and(Iterable<? extends Predicate<? super C>> components) {
		return Predicates.and(Iterables.concat(ImmutableList.of(this), components));
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if this flag and each of the provided
	 * components components evaluates to {@code true}. The components are evaluated in order (after
	 * the flag), and evaluation will be "short-circuited" as soon as a false predicate is found. It
	 * defensively copies the array passed in, so future changes to it won't alter the behavior of
	 * this predicate.
	 */
	public final Predicate<C> and(Predicate<? super C>... components) {
		return and(Arrays.asList(components));
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if both this flag and the provided predicate
	 * evaluate to {@code true}. The component is evaluated after the flag and evaluation will be
	 * "short-circuited" as soon as a false predicate is found.
	 */
	public final Predicate<C> and(Predicate<? super C> other) {
		return and(ImmutableList.of(other));
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if any one of this flag or the provided
	 * components evaluates to {@code true}. The components are evaluated in order (after the flag),
	 * and evaluation will be "short-circuited" as soon as a true predicate is found. It defensively
	 * copies the iterable passed in, so future changes to it won't alter the behavior of this
	 * predicate.
	 */
	public final Predicate<C> or(Iterable<? extends Predicate<? super C>> components) {
		return Predicates.or(Iterables.concat(ImmutableList.of(this), components));
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if any one of this flag or the provided
	 * components evaluates to {@code true}. The components are evaluated in order (after the flag),
	 * and evaluation will be "short-circuited" as soon as a true predicate is found. It defensively
	 * copies the iterable passed in, so future changes to it won't alter the behavior of this
	 * predicate.
	 */
	public final Predicate<C> or(Predicate<? super C>... components) {
		return or(Arrays.asList(components));
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if any of this flag or the provided
	 * predicate evaluate to {@code true}. The argument is evaluated after the flag and evaluation
	 * will be "short-circuited" as soon as a true predicate is found.
	 */
	public final Predicate<C> or(Predicate<? super C> other) {
		return or(ImmutableList.of(other));
	}

	/**
	 * Flag view as a property.
	 */
	private final class FlagProperty extends BooleanMetaProperty<C> {
		FlagProperty() {
			super(MetaFlag.this.getName(), true, Predicates.alwaysTrue(), defaultValue);
		}

		private MetaFlag<C> get() {
			return MetaFlag.this;
		}

		public Boolean apply(C input) {
			return get().apply(input);
		}

		@Override
		public int hashCode() {
			return get().hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof MetaFlag.FlagProperty) {
				return get() == getClass().cast(obj).get();
			}
			return false;
		}
	}

}
