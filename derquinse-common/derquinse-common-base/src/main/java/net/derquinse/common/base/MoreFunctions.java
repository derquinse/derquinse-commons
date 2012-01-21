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
package net.derquinse.common.base;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Optional;

/**
 * Static utility methods pertaining to {@code Function} instances.
 * <p>
 * All methods returns serializable functions as long as they're given serializable parameters.
 * @author Andres Rodriguez
 */
public final class MoreFunctions extends NotInstantiable {
	/** Not instantiable. */
	private MoreFunctions() {
	}

	/**
	 * Returns a functions that always returns null for a null argument and the result of the provided
	 * function otherwise.
	 */
	public static <F, T> Function<F, T> nullSafe(Function<F, T> f) {
		return new NullSafeFunction<F, T>(f);
	}

	/** Returns a function invocation of {@link Optional#fromNullable(Object)}. */
	public static <T> Function<T, Optional<T>> fromNullable() {
		return new FromNullable<T>();
	}

	// End public API, begin private implementation classes.

	/** @see MoreFunctions#nullSafe(Function) */
	private static class NullSafeFunction<F, T> implements Function<F, T>, Serializable {
		private final Function<F, T> delegate;

		private NullSafeFunction(Function<F, T> delegate) {
			this.delegate = checkNotNull(delegate, "The function to decorate must be provided");
		}

		@Override
		public T apply(F from) {
			if (from == null) {
				return null;
			}
			return delegate.apply(from);
		}

		@Override
		public int hashCode() {
			return delegate.hashCode();
		}

		@Override
		public boolean equals(@Nullable Object obj) {
			if (obj instanceof NullSafeFunction) {
				NullSafeFunction<?, ?> that = (NullSafeFunction<?, ?>) obj;
				return delegate.equals(that.delegate);
			}
			return false;
		}

		@Override
		public String toString() {
			return "NullSafe(" + delegate + ")";
		}

		private static final long serialVersionUID = 0;
	}

	/** @see MoreFunctions#fromNullable() */
	private static class FromNullable<T> implements Function<T, Optional<T>>, Serializable {
		private FromNullable() {
		}

		@Override
		public Optional<T> apply(T from) {
			return Optional.fromNullable(from);
		}

		@Override
		public int hashCode() {
			return getClass().hashCode();
		}

		@Override
		public boolean equals(@Nullable Object obj) {
			return (obj instanceof FromNullable);
		}

		@Override
		public String toString() {
			return "FromNullable";
		}

		private static final long serialVersionUID = 0;
	}

}
