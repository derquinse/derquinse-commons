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
package net.derquinse.common.product;

import com.google.common.base.Function;
import com.google.common.base.Objects;

/**
 * Helper functions to operate on products.
 * @author Andres Rodriguez
 */
public final class Products {
	/** Not instantiable. */
	private Products() {
		throw new AssertionError();
	}

	/**
	 * Generates the hash code for a product object.
	 * @param p Product.
	 * @return The hash code of the argument or 0 if the argument is {@code null}.
	 */
	public static int hashCode(Product p) {
		if (p == null) {
			return 0;
		}
		int h = 17;
		for (int i = 0; i < p.arity(); i++) {
			Object o = p.get(i);
			int c = (o == null) ? 0 : o.hashCode();
			h = 31 * h + c;
		}
		return h;
	}

	/**
	 * Determines whether two possibly-null products are equal. It is assumed that any non-null
	 * product elements passed to this function conform to the equals() contract
	 * @param a First product.
	 * @param b Second product.
	 * @return True if both products are {@null} or if they are both non-{@code null}, have the
	 *         same arity and their elements are equal. False otherwise.
	 */
	public static boolean equal(Product a, Product b) {
		if (a == b || (a == null && b == null)) {
			return true;
		}
		if (a != null && b != null && a.arity() == b.arity()) {
			for (int i = 0; i < a.arity(); i++) {
				if (!Objects.equal(a.get(i), b.get(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * First element extractor.
	 * @param t Tuple.
	 * @return The first element or {@code null} if the argument is {@code null}. hello
	 */
	public static <T0> T0 get0(Product1<T0> t) {
		if (t == null) {
			return null;
		}
		return t.get0();
	}

	/**
	 * Second element extractor.
	 * @param t Tuple.
	 * @return The second element or {@code null} if the argument is {@code null}.
	 */
	public static <T0, T1> T1 get1(Product2<T0, T1> t) {
		if (t == null) {
			return null;
		}
		return t.get1();
	}

	/**
	 * Third element extractor.
	 * @param t Tuple.
	 * @return The third element or {@code null} if the argument is {@code null} .
	 */
	public static <T0, T1, T2> T2 get2(Product3<T0, T1, T2> t) {
		if (t == null) {
			return null;
		}
		return t.get2();
	}

	/**
	 * First element getter.
	 * @return A function returning the first element or {@code null} if the argument is {@code null}
	 *         .
	 */
	public static <T0> Function<Product1<T0>, T0> getter0() {
		return new Function<Product1<T0>, T0>() {
			public T0 apply(Product1<T0> from) {
				return get0(from);
			}

			@Override
			public String toString() {
				return "getter0";
			}
		};
	}

	/**
	 * Second element getter.
	 * @return A function returning the second element or {@code null} if the argument is {@code null}
	 *         .
	 */
	public static <T0, T1> Function<Product2<T0, T1>, T1> getter1() {
		return new Function<Product2<T0, T1>, T1>() {
			public T1 apply(Product2<T0, T1> from) {
				return get1(from);
			}

			@Override
			public String toString() {
				return "getter1";
			}
		};
	}

	/**
	 * Third element getter.
	 * @return A function returning the third element or {@code null} if the argument is {@code null}
	 *         .
	 */
	public static <T0, T1, T2> Function<Product3<T0, T1, T2>, T2> getter2() {
		return new Function<Product3<T0, T1, T2>, T2>() {
			public T2 apply(Product3<T0, T1, T2> from) {
				return get2(from);
			}

			@Override
			public String toString() {
				return "getter2";
			}
		};
	}
}
