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
package net.derquinse.common.base;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

/**
 * Encapsulation of an optional value.
 * @param <V> Value type.
 * @author Andres Rodriguez
 */
public abstract class Option<V> implements Supplier<V> {
	/** None singleton. */
	private static final None<Object> NONE = new None<Object>();

	/**
	 * Returns the none option.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Option<T> none() {
		return (Option<T>) NONE;
	}

	/**
	 * Returns a defined option.
	 * @throws NullPointerException if th argument is {@code null}.
	 */
	public static <T> Option<T> some(T value) {
		return new Some<T>(value);
	}

	/**
	 * Returns a defined option or none if the argument is {@code null}.
	 */
	public static <T> Option<T> of(@Nullable T value) {
		if (value != null) {
			return some(value);
		}
		return none();
	}

	/**
	 * Returns the {@link #some(Object)} method as a function.
	 */
	public static <T> Function<T, Option<T>> some() {
		return new SomeFunction<T>();
	}

	/**
	 * Returns the {@link #of(Object)} method as a function.
	 */
	public static <T> Function<T, Option<T>> of() {
		return new OfFunction<T>();
	}

	/** Constructor. */
	private Option() {
	}

	/**
	 * Returns {@code true} if the Options contains some value.
	 */
	public abstract boolean isDefined();

	/**
	 * Returns {@code true} if the Options contains no value.
	 */
	public abstract boolean isEmpty();

	/**
	 * Returns the contained value.
	 * @throws IllegalStateException if the option contains no value.
	 */
	public abstract V get();

	/**
	 * Returns the contained value if the option is defined or the argument otherwise.
	 * @throws IllegalStateException if the option contains no value.
	 */
	public abstract V getOrElse(@Nullable V fallback);

	private static final class Some<V> extends Option<V> {
		private final V value;

		Some(V value) {
			this.value = checkNotNull(value, "Some value cannot be null");
		}

		@Override
		public boolean isDefined() {
			return true;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public V get() {
			return value;
		}

		@Override
		public V getOrElse(@Nullable V fallback) {
			return value;
		};

		@Override
		public int hashCode() {
			return value.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Some<?>) {
				return value.equals(((Some<?>) obj).value);
			}
			return false;
		}
	}

	private static final class None<V> extends Option<V> {
		None() {
		}

		@Override
		public boolean isDefined() {
			return false;
		}

		@Override
		public boolean isEmpty() {
			return true;
		}

		@Override
		public V get() {
			throw new IllegalStateException("None option");
		}

		@Override
		public V getOrElse(@Nullable V fallback) {
			return fallback;
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof None<?>;
		}
	}

	/** Method {@link #some(Object)} as a function. */
	private static final class SomeFunction<T> implements Function<T, Option<T>> {
		public Option<T> apply(T from) {
			return some(from);
		}
	}

	/** Method {@link #of(Object)} as a function. */
	private static final class OfFunction<T> implements Function<T, Option<T>> {
		public Option<T> apply(T from) {
			return of(from);
		}
	}

}
