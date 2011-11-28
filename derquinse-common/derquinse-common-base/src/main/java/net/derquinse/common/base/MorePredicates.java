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

import java.io.Serializable;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * Static utility methods pertaining to {@code Predicate} instances.
 * <p>
 * All methods returns serializable predicates as long as they're given serializable parameters.
 * @author Andres Rodriguez
 */
public final class MorePredicates {
	/** Not instantiable. */
	private MorePredicates() {
		throw new AssertionError();
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the object being tested is the same
	 * (identity equality) as the given target or both are null.
	 */
	public static <T> Predicate<T> sameAs(@Nullable T target) {
		return (target == null) ? Predicates.<T> isNull() : new IsSameAsPredicate<T>(target);
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

}
