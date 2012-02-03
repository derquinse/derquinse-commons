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

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * Static utility methods pertaining to {@code Predicate} instances.
 * <p>
 * All methods returns serializable predicates as long as they're given serializable parameters.
 * @author Andres Rodriguez
 */
public final class MorePredicates extends NotInstantiable {
	/** Not instantiable. */
	private MorePredicates() {
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the object being tested is the same
	 * (identity equality) as the given target or both are null.
	 */
	public static <T> Predicate<T> sameAs(@Nullable T target) {
		return (target == null) ? Predicates.<T> isNull() : new IsSameAsPredicate<T>(target);
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the object being tested is greater than
	 * the given value. The returned predicate does not allow null inputs.
	 */
	public static <T extends Comparable<T>> Predicate<T> greaterThan(T value) {
		return new GreaterThan<T>(value);
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the object being tested is less than the
	 * given value. The returned predicate does not allow null inputs.
	 */
	public static <T extends Comparable<T>> Predicate<T> lessThan(T value) {
		return new LessThan<T>(value);
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the object being tested is greater than
	 * or equal to the given value. The returned predicate does not allow null inputs.
	 */
	public static <T extends Comparable<T>> Predicate<T> greaterOrEqual(T value) {
		return new GreaterOrEqual<T>(value);
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the object being tested is less than or
	 * equal to the given value. The returned predicate does not allow null inputs.
	 */
	public static <T extends Comparable<T>> Predicate<T> lessOrEqual(T value) {
		return new LessOrEqual<T>(value);
	}

	// End public API, begin private implementation classes.

	/** @see MorePredicates#sameAs(Object) */
	private static class IsSameAsPredicate<T> implements Predicate<T>, Serializable {
		private final T target;

		private IsSameAsPredicate(T target) {
			this.target = target;
		}

		@Override
		public boolean apply(T t) {
			return target == t;
		}

		@Override
		public int hashCode() {
			return target.hashCode();
		}

		@Override
		public boolean equals(@Nullable Object obj) {
			if (obj instanceof IsSameAsPredicate) {
				IsSameAsPredicate<?> that = (IsSameAsPredicate<?>) obj;
				return target == that.target;
			}
			return false;
		}

		@Override
		public String toString() {
			return "IsSameAs(" + target + ")";
		}

		private static final long serialVersionUID = 0;
	}

	private static abstract class ComparisonPredicate<T extends Comparable<T>> implements Predicate<T>, Serializable {
		private final T value;

		ComparisonPredicate(T value) {
			this.value = checkNotNull(value, "The comparison reference must be provided");
		}

		@Override
		public final boolean apply(T t) {
			return apply(checkNotNull(t, "The value to compare must be provided").compareTo(value));
		}

		abstract boolean apply(int result);

		@Override
		public final int hashCode() {
			return Objects.hashCode(value, getClass());
		}

		@Override
		public final boolean equals(@Nullable Object obj) {
			if (obj instanceof ComparisonPredicate) {
				ComparisonPredicate<?> other = (ComparisonPredicate<?>) obj;
				return getClass() == other.getClass() && value.equals(other.value);
			}
			return false;
		}

		@Override
		public String toString() {
			return String.format("%s(%s)", getClass().getSimpleName(), value);
		}

		private static final long serialVersionUID = 0;
	}

	/** @see MorePredicates#greaterThan(Comparable) */
	private static class GreaterThan<T extends Comparable<T>> extends ComparisonPredicate<T> {
		private GreaterThan(T value) {
			super(value);
		}

		@Override
		public boolean apply(int result) {
			return result > 0;
		}

		private static final long serialVersionUID = 0;
	}

	/** @see MorePredicates#lessThan(Comparable) */
	private static class LessThan<T extends Comparable<T>> extends ComparisonPredicate<T> {
		private LessThan(T value) {
			super(value);
		}

		@Override
		public boolean apply(int result) {
			return result < 0;
		}

		private static final long serialVersionUID = 0;
	}

	/** @see MorePredicates#greaterOrEqual(Comparable) */
	private static class GreaterOrEqual<T extends Comparable<T>> extends ComparisonPredicate<T> {
		private GreaterOrEqual(T value) {
			super(value);
		}

		@Override
		public boolean apply(int result) {
			return result >= 0;
		}

		private static final long serialVersionUID = 0;
	}

	/** @see MorePredicates#lessOrEqual(Comparable) */
	private static class LessOrEqual<T extends Comparable<T>> extends ComparisonPredicate<T> {
		private LessOrEqual(T value) {
			super(value);
		}

		@Override
		public boolean apply(int result) {
			return result <= 0;
		}

		private static final long serialVersionUID = 0;
	}

}
