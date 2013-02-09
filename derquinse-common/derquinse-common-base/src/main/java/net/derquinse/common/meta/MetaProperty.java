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

import java.util.Collection;

import javax.annotation.Nullable;

import net.derquinse.common.base.MorePredicates;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * Base class for property descriptors.
 * @author Andres Rodriguez
 * @param <C> Containing type.
 * @param <T> Property type.
 */
public abstract class MetaProperty<C, T> extends Meta<C> implements RequiredFlag, Function<C, T> {
	/** Whether the property is required. */
	private final boolean required;
	/** Validity predicate. */
	private final Predicate<? super T> validity;
	/** Default value. */
	private final T defaultValue;

	/**
	 * Default constructor.
	 * @param name Property name.
	 * @param required True if the property is required.
	 * @param validity Validity predicate.
	 * @param defaultValue Default value for the property.
	 */
	protected MetaProperty(String name, boolean required, @Nullable Predicate<? super T> validity,
			@Nullable T defaultValue) {
		super(name);
		this.required = required;
		if (validity != null) {
			this.validity = validity;
		} else {
			this.validity = Predicates.alwaysTrue();
		}
		this.defaultValue = defaultValue;
	}

	/**
	 * Constructor.
	 * @param name Property name.
	 * @param required True if the property is required.
	 * @param validity Validity predicate.
	 */
	protected MetaProperty(String name, boolean required, @Nullable Predicate<? super T> validity) {
		this(name, required, validity, null);
	}

	/**
	 * Constructor.
	 * @param name Property name.
	 * @param required True if the property is required.
	 */
	protected MetaProperty(String name, boolean required) {
		this(name, required, null);
	}

	/**
	 * Returns whether the property is required.
	 */
	@Override
	public final boolean isRequired() {
		return required;
	}

	/**
	 * Returns the default value.
	 * @return The default value or {@code null} if the property has no default value.
	 */
	public final T getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Checks a value for validity.
	 * @param value Value to check.
	 * @return The same value.
	 * @throws NullPointerException if the argument is {@code null} and the property is required.
	 * @throws IllegalArgumentException if the argument is not valid.
	 */
	public final T checkValue(@Nullable T value) {
		if (value == null) {
			if (required) {
				throw new NullPointerException(String.format("Property [%s] does not allow null values", getName()));
			}
		} else {
			if (!validity.apply(value)) {
				String msg = String.format("Illegal value [%s] for property [%s]", value.toString(), getName());
				throw new IllegalArgumentException(msg);
			}
		}
		return value;
	}

	/**
	 * Returns whether a value is valid.
	 * @param value Value to check.
	 * @return True if the value is valid.
	 */
	public final boolean isValid(@Nullable T value) {
		try {
			checkValue(value);
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * Returns the default value.
	 * @return The default value.
	 * @throws IllegalStateException if the property is required but has no default value.
	 */
	private T getDefault() {
		if (required && defaultValue == null) {
			throw new IllegalStateException(String.format("No default value for required property [%s]", getName()));
		}
		return defaultValue;
	}

	/**
	 * Returns the default value if the provided value is {@code null}.
	 * @param value Provided value.
	 * @return The provided value if the argument is non-null, the default value otherwise.
	 * @throws IllegalStateException if the property is required but has no default value.
	 */
	public final T getDefaultIfNull(@Nullable T value) {
		if (value != null) {
			return value;
		}
		return getDefault();
	}

	/**
	 * Returns the default value if the provided value is invalid.
	 * @param value Provided value.
	 * @return The provided value if the argument is valid, the default value otherwise.
	 * @throws IllegalStateException if the property is required but has no default value.
	 */
	public final T getDefaultIfInvalid(@Nullable T value) {
		if (isValid(value)) {
			return value;
		}
		return getDefault();
	}

	/**
	 * Returns the composition of this property function with another that tranform the value.
	 * @param g the second function to apply
	 * @return The composition of this funcion and {@code g}
	 */
	public final <V> Function<C, V> compose(Function<T, V> g) {
		return Functions.compose(g, this);
	}

	/**
	 * Returns the composition of this property function and a predicate on the value.
	 * @return The composition of this function and the provided predicate.
	 */
	public final Predicate<C> compose(Predicate<? super T> predicate) {
		return Predicates.compose(predicate, this);
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the property value is null.
	 */
	public final Predicate<C> isNull() {
		return compose(Predicates.isNull());
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the property value being tested is not
	 * null.
	 */
	public final Predicate<C> notNull() {
		return compose(Predicates.notNull());
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the property value {@code equals()} the
	 * given target or both are null.
	 */
	public final Predicate<C> equalTo(@Nullable T target) {
		return compose(Predicates.equalTo(target));
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the property value is the same (identity
	 * equality) as the given target or both are null.
	 */
	public final Predicate<C> sameAs(@Nullable T target) {
		return compose(MorePredicates.sameAs(target));
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the property value tested is an instance
	 * of the given class. If the property value being tested is {@code null} this predicate evaluates
	 * to {@code false}.
	 * <p>
	 * <b>Warning:</b> contrary to the typical assumptions about predicates (as documented at
	 * {@link Predicate#apply}), the returned predicate may not be <i>consistent with equals</i>. For
	 * example, {@code instanceOf(ArrayList.class)} will yield different results for the two equal
	 * instances {@code Lists.newArrayList(1)} and {@code Arrays.asList(1)}.
	 */
	public final Predicate<C> instanceOf(Class<?> clazz) {
		return compose(Predicates.instanceOf(clazz));
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the property value being tested is a
	 * member of the given collection. It does not defensively copy the collection passed in, so
	 * future changes to it will alter the behavior of the predicate. This method can technically
	 * accept any Collection<?>, but using a typed collection helps prevent bugs. This approach
	 * doesn't block any potential users since it is always possible to use
	 * {@code Predicates.<Object>in()}.
	 * @param target The collection that may contain the property value.
	 */
	public final Predicate<C> in(Collection<? extends T> target) {
		return compose(Predicates.in(target));
	}

	@Override
	public String toString() {
		final Metas.ToStringHelper<?> h = Metas.toStringHelper(this).add(NAME).add(REQUIRED);
		if (defaultValue != null) {
			h.add("defaultValue", defaultValue);
		}
		if (!Predicates.alwaysTrue().equals(validity)) {
			h.add("validity", validity);
		}
		return h.toString();
	}
}
