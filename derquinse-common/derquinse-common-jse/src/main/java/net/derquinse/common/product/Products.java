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

	/**
	 * Returns the empty tuple.
	 * @return The empty tuple.
	 */
	public static Tuple tuple() {
		return EmptyProduct.INSTANCE;
	}

	/**
	 * Builds a 1-element tuple.
	 * @param e Element.
	 * @return The requested tuple.
	 */
	public static <T1> Tuple1<T1> tuple(T1 e) {
		return new Tuple1Impl<T1>(e);
	}

	/**
	 * Builds a 1-element tuple.
	 * @param e Element.
	 * @return The requested tuple.
	 */
	public static <T1> Tuple1<T1> tuple(Product1<T1> e) {
		if (e instanceof Tuple1<?>) {
			return (Tuple1<T1>) e;
		}
		return tuple(get0(e));
	}

	/**
	 * Builds a 2-element tuple.
	 * @param e0 First element.
	 * @param e1 Second element.
	 * @return The requested tuple.
	 */
	public static <T0, T1> Tuple2<T0, T1> tuple(T0 e0, T1 e1) {
		return new Tuple2Impl<T0, T1>(e0, e1);
	}

	/**
	 * Builds a 2-element tuple.
	 * @param t0 First element.
	 * @param e1 Second element.
	 * @return The requested tuple.
	 */
	public static <T0, T1> Tuple2<T0, T1> tuple(Product1<T0> t0, T1 e1) {
		return tuple(get0(t0), e1);
	}

	/**
	 * Builds a 2-element tuple.
	 * @param e0 First element.
	 * @param t1 Second element.
	 * @return The requested tuple.
	 */
	public static <T0, T1> Tuple2<T0, T1> tuple(T0 e0, Product1<T1> t1) {
		return tuple(e0, get0(t1));
	}

	/**
	 * Builds a 2-element tuple.
	 * @param t0 First element.
	 * @param t1 Second element.
	 * @return The requested tuple.
	 */
	public static <T0, T1> Tuple2<T0, T1> tuple(Product1<T0> t0, Product1<T1> t1) {
		return tuple(get0(t0), get0(t1));
	}

	/**
	 * Builds a 3-element tuple.
	 * @param e0 First element.
	 * @param e1 Second element.
	 * @param e2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(T0 e0, T1 e1, T2 e2) {
		return new Tuple3Impl<T0, T1, T2>(e0, e1, e2);
	}

	/**
	 * Builds a 3-element tuple.
	 * @param t0 First element.
	 * @param e1 Second element.
	 * @param e2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(Product1<T0> t0, T1 e1, T2 e2) {
		return tuple(get0(t0), e1, e2);
	}

	/**
	 * Builds a 3-element tuple.
	 * @param e0 First element.
	 * @param t1 Second element.
	 * @param e2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(T0 e0, Product1<T1> t1, T2 e2) {
		return tuple(e0, get0(t1), e2);
	}

	/**
	 * Builds a 3-element tuple.
	 * @param e0 First element.
	 * @param e1 Second element.
	 * @param t2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(T0 e0, T1 e1, Product1<T2> t2) {
		return tuple(e0, e1, get0(t2));
	}

	/**
	 * Builds a 3-element tuple.
	 * @param t0 First element.
	 * @param t1 Second element.
	 * @param e2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(Product1<T0> t0, Product1<T1> t1, T2 e2) {
		return tuple(get0(t0), get0(t1), e2);
	}

	/**
	 * Builds a 3-element tuple.
	 * @param t0 First element.
	 * @param e1 Second element.
	 * @param t2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(Product1<T0> t0, T1 e1, Product1<T2> t2) {
		return tuple(get0(t0), e1, get0(t2));
	}

	/**
	 * Builds a 3-element tuple.
	 * @param e0 First element.
	 * @param t1 Second element.
	 * @param t2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(T0 e0, Product1<T1> t1, Product1<T2> t2) {
		return tuple(e0, get0(t1), get0(t2));
	}

	/**
	 * Builds a 3-element tuple.
	 * @param t0 First element.
	 * @param t1 Second element.
	 * @param t2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(Product1<T0> t0, Product1<T1> t1, Product1<T2> t2) {
		return tuple(get0(t0), get0(t1), get0(t2));
	}

	/**
	 * Builds a 3-element tuple.
	 * @param p0_1 Elements 0-1.
	 * @param e2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(Product2<T0, T1> p0_1, T2 e2) {
		return tuple(get0(p0_1), get1(p0_1), e2);
	}

	/**
	 * Builds a 3-element tuple.
	 * @param p0_1 Elements 0-1.
	 * @param e2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(Product2<T0, T1> p0_1, Tuple1<T2> e2) {
		return tuple(p0_1, get0(e2));
	}

	/**
	 * Builds a 3-element tuple.
	 * @param e0 First element.
	 * @param p1_2 Elements 1-2.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(T0 e0, Product2<T1, T2> p1_2) {
		return tuple(e0, get0(p1_2), get1(p1_2));
	}

	/**
	 * Builds a 3-element tuple.
	 * @param e0 First element.
	 * @param p1_2 Elements 1-2.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(Product1<T0> e0, Product2<T1, T2> p1_2) {
		return tuple(get0(e0), p1_2);
	}

	/**
	 * Returns a function transforming the first element of a 1-element product. The returned function
	 * returns {@code null} for {@code null} inputs.
	 * @param f Element transformation function.
	 * @return The requested function.
	 */
	public static <T0, T> Function<Product1<T0>, Tuple1<T>> p0toTuple1(final Function<T0, T> f) {
		return new Function<Product1<T0>, Tuple1<T>>() {
			public Tuple1<T> apply(Product1<T0> from) {
				if (from == null) {
					return null;
				}
				return tuple(f.apply(from.get0()));
			}

			@Override
			public String toString() {
				return String.format("p1 -> tuple(%s)", f);
			}
		};
	}

	/**
	 * Returns a function transforming the first element of a 2-element product. The returned function
	 * returns {@code null} for {@code null} inputs.
	 * @param f Element transformation function.
	 * @return The requested function.
	 */
	public static <T0, T1, T> Function<Product2<T0, T1>, Tuple2<T, T1>> p0toTuple2(final Function<T0, T> f) {
		return new Function<Product2<T0, T1>, Tuple2<T, T1>>() {
			public Tuple2<T, T1> apply(Product2<T0, T1> from) {
				if (from == null) {
					return null;
				}
				return tuple(f.apply(from.get0()), from.get1());
			}

			@Override
			public String toString() {
				return String.format("p2 -> tuple(%s,_)", f);
			}
		};
	}

	/**
	 * Returns a function transforming the first element of a 3-element product. The returned function
	 * returns {@code null} for {@code null} inputs.
	 * @param f Element transformation function.
	 * @return The requested function.
	 */
	public static <T0, T1, T2, T> Function<Product3<T0, T1, T2>, Tuple3<T, T1, T2>> p0toTuple3(final Function<T0, T> f) {
		return new Function<Product3<T0, T1, T2>, Tuple3<T, T1, T2>>() {
			public Tuple3<T, T1, T2> apply(Product3<T0, T1, T2> from) {
				if (from == null) {
					return null;
				}
				return tuple(f.apply(from.get0()), from.get1(), from.get2());
			}

			@Override
			public String toString() {
				return String.format("p3 -> tuple(%s,_,_)", f);
			}
		};
	}

	/**
	 * Returns a function transforming the second element of a 2-element product. The returned
	 * function returns {@code null} for {@code null} inputs.
	 * @param f Element transformation function.
	 * @return The requested function.
	 */
	public static <T0, T1, T> Function<Product2<T0, T1>, Tuple2<T0, T>> p1toTuple2(final Function<T1, T> f) {
		return new Function<Product2<T0, T1>, Tuple2<T0, T>>() {
			public Tuple2<T0, T> apply(Product2<T0, T1> from) {
				if (from == null) {
					return null;
				}
				return tuple(from.get0(), f.apply(from.get1()));
			}

			@Override
			public String toString() {
				return String.format("p2 -> tuple(_,%s)", f);
			}
		};
	}

	/**
	 * Returns a function transforming the second element of a 3-element product. The returned
	 * function returns {@code null} for {@code null} inputs.
	 * @param f Element transformation function.
	 * @return The requested function.
	 */
	public static <T0, T1, T2, T> Function<Product3<T0, T1, T2>, Tuple3<T0, T, T2>> p1toTuple3(final Function<T1, T> f) {
		return new Function<Product3<T0, T1, T2>, Tuple3<T0, T, T2>>() {
			public Tuple3<T0, T, T2> apply(Product3<T0, T1, T2> from) {
				if (from == null) {
					return null;
				}
				return tuple(from.get0(), f.apply(from.get1()), from.get2());
			}

			@Override
			public String toString() {
				return String.format("p3 -> tuple(_,%s,_)", f);
			}
		};
	}

	/**
	 * Returns a function transforming the third element of a 3-element product. The returned function
	 * returns {@code null} for {@code null} inputs.
	 * @param f Element transformation function.
	 * @return The requested function.
	 */
	public static <T0, T1, T2, T> Function<Product3<T0, T1, T2>, Tuple3<T0, T1, T>> p2toTuple3(final Function<T2, T> f) {
		return new Function<Product3<T0, T1, T2>, Tuple3<T0, T1, T>>() {
			public Tuple3<T0, T1, T> apply(Product3<T0, T1, T2> from) {
				if (from == null) {
					return null;
				}
				return tuple(from.get0(), from.get1(), f.apply(from.get2()));
			}

			@Override
			public String toString() {
				return String.format("p3 -> tuple(_,_,%s)", f);
			}
		};
	}

	/**
	 * Returns the empty power.
	 * @return The empty power.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Power<T> power() {
		return (Power<T>) EmptyProduct.INSTANCE;
	}

	/**
	 * Builds a singleton.
	 * @param e Element.
	 * @return The requested singleton.
	 */
	public static <T> Singleton<T> singleton(T e) {
		return new SingletonImpl<T>(e);
	}

	/**
	 * Builds a singleton.
	 * @param e Element.
	 * @return The requested singleton.
	 */
	public static <T> Singleton<T> singleton(Product1<T> e) {
		if (e instanceof SingletonImpl<?>) {
			return (Singleton<T>) e;
		}
		return singleton(get0(e));
	}

	/**
	 * Builds a pair.
	 * @param e0 First element.
	 * @param e1 Second element.
	 * @return The requested tuple.
	 */
	public static <T> Pair<T> pair(T e0, T e1) {
		return new PairImpl<T>(e0, e1);
	}

	/**
	 * Builds a pair.
	 * @param t0 First element.
	 * @param e1 Second element.
	 * @return The requested tuple.
	 */
	public static <T> Pair<T> pair(Product1<T> t0, T e1) {
		return pair(get0(t0), e1);
	}

	/**
	 * Builds a pair.
	 * @param e0 First element.
	 * @param t1 Second element.
	 * @return The requested tuple.
	 */
	public static <T> Pair<T> pair(T e0, Product1<T> t1) {
		return pair(e0, get0(t1));
	}

	/**
	 * Builds a pair.
	 * @param t0 First element.
	 * @param t1 Second element.
	 * @return The requested tuple.
	 */
	public static <T> Pair<T> pair(Product1<T> t0, Product1<T> t1) {
		return pair(get0(t0), get0(t1));
	}

}
