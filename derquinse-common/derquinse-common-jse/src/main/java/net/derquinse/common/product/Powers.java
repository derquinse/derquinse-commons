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

import static net.derquinse.common.product.Products.get0;
import static net.derquinse.common.product.Products.get1;

import com.google.common.base.Function;

/**
 * Helper functions to operate on powers.
 * @author Andres Rodriguez
 */
public final class Powers {
	/** Not instantiable. */
	private Powers() {
		throw new AssertionError();
	}

	/**
	 * Returns the empty power.
	 * @return The empty power.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Power<T> power() {
		return (Power<T>) Power0Impl.POWER0;
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
		if (e instanceof Singleton<?>) {
			return (Singleton<T>) e;
		}
		return singleton(get0(e));
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
	public static <T0, T1> Tuple2<T0, T1> tuple2(Tuple1<T0> t0, T1 e1) {
		return tuple(get0(t0), e1);
	}

	/**
	 * Builds a 2-element tuple.
	 * @param e0 First element.
	 * @param t1 Second element.
	 * @return The requested tuple.
	 */
	public static <T0, T1> Tuple2<T0, T1> tuple2(T0 e0, Tuple1<T1> t1) {
		return tuple(e0, get0(t1));
	}

	/**
	 * Builds a 2-element tuple.
	 * @param t0 First element.
	 * @param t1 Second element.
	 * @return The requested tuple.
	 */
	public static <T0, T1> Tuple2<T0, T1> tuple(Tuple1<T0> t0, Tuple1<T1> t1) {
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
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(Tuple1<T0> t0, T1 e1, T2 e2) {
		return tuple(get0(t0), e1, e2);
	}

	/**
	 * Builds a 3-element tuple.
	 * @param e0 First element.
	 * @param t1 Second element.
	 * @param e2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(T0 e0, Tuple1<T1> t1, T2 e2) {
		return tuple(e0, get0(t1), e2);
	}

	/**
	 * Builds a 3-element tuple.
	 * @param e0 First element.
	 * @param e1 Second element.
	 * @param t2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(T0 e0, T1 e1, Tuple1<T2> t2) {
		return tuple(e0, e1, get0(t2));
	}

	/**
	 * Builds a 3-element tuple.
	 * @param t0 First element.
	 * @param t1 Second element.
	 * @param e2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(Tuple1<T0> t0, Tuple1<T1> t1, T2 e2) {
		return tuple(get0(t0), get0(t1), e2);
	}

	/**
	 * Builds a 3-element tuple.
	 * @param t0 First element.
	 * @param e1 Second element.
	 * @param t2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(Tuple1<T0> t0, T1 e1, Tuple1<T2> t2) {
		return tuple(get0(t0), e1, get0(t2));
	}

	/**
	 * Builds a 3-element tuple.
	 * @param e0 First element.
	 * @param t1 Second element.
	 * @param t2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(T0 e0, Tuple1<T1> t1, Tuple1<T2> t2) {
		return tuple(e0, get0(t1), get0(t2));
	}

	/**
	 * Builds a 3-element tuple.
	 * @param t0 First element.
	 * @param t1 Second element.
	 * @param t2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(Tuple1<T0> t0, Tuple1<T1> t1, Tuple1<T2> t2) {
		return tuple(get0(t0), get0(t1), get0(t2));
	}

	/**
	 * Builds a 3-element tuple.
	 * @param t0_1 Elements 0-1.
	 * @param e2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(Tuple2<T0, T1> t0_1, T2 e2) {
		return tuple(get0(t0_1), get1(t0_1), e2);
	}

	/**
	 * Builds a 3-element tuple.
	 * @param t0_1 Elements 0-1.
	 * @param t2 Third element.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(Tuple2<T0, T1> t0_1, Tuple1<T2> t2) {
		return tuple(t0_1, get0(t2));
	}

	/**
	 * Builds a 3-element tuple.
	 * @param e0 First element.
	 * @param t1_2 Elements 1-2.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(T0 e0, Tuple2<T1, T2> t1_2) {
		return tuple(e0, get0(t1_2), get1(t1_2));
	}

	/**
	 * Builds a 3-element tuple.
	 * @param t0 First element.
	 * @param t1_2 Elements 1-2.
	 * @return The requested tuple.
	 */
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple(Tuple1<T0> t0, Tuple2<T1, T2> t1_2) {
		return tuple(get0(t0), t1_2);
	}

	/**
	 * Returns a function transforming the first element of a 1-element product. The returned function
	 * returns {@code null} for {@code null} inputs.
	 * @param f Element transformation function.
	 * @return The requested function.
	 */
	public static <T0, T> Function<Product1<T0>, Singleton<T>> p0toSingleton(final Function<T0, T> f) {
		return new Function<Product1<T0>, Singleton<T>>() {
			public Singleton<T> apply(Product1<T0> from) {
				if (from == null) {
					return null;
				}
				return singleton(f.apply(from.get0()));
			}

			@Override
			public String toString() {
				return String.format("p1 -> singleton(%s)", f);
			}
		};
	}

	/**
	 * Returns a function transforming the first element of a 2-element tuple. The returned function
	 * returns {@code null} for {@code null} inputs.
	 * @param f Element transformation function.
	 * @return The requested function.
	 */
	public static <T0, T1, T> Function<Tuple2<T0, T1>, Tuple2<T, T1>> transformer2_0(final Function<T0, T> f) {
		return new Function<Tuple2<T0, T1>, Tuple2<T, T1>>() {
			public Tuple2<T, T1> apply(Tuple2<T0, T1> from) {
				if (from == null) {
					return null;
				}
				return tuple(f.apply(from.get0()), from.get1());
			}

			@Override
			public String toString() {
				return "transformer2_0";
			}
		};
	}

	/**
	 * Returns a function transforming the first element of a 3-element tuple. The returned function
	 * returns {@code null} for {@code null} inputs.
	 * @param f Element transformation function.
	 * @return The requested function.
	 */
	public static <T0, T1, T2, T> Function<Tuple3<T0, T1, T2>, Tuple3<T, T1, T2>> transformer3_0(final Function<T0, T> f) {
		return new Function<Tuple3<T0, T1, T2>, Tuple3<T, T1, T2>>() {
			public Tuple3<T, T1, T2> apply(Tuple3<T0, T1, T2> from) {
				if (from == null) {
					return null;
				}
				return tuple(f.apply(from.get0()), from.get1(), from.get2());
			}

			@Override
			public String toString() {
				return "transformer3_0";
			}
		};
	}

	/**
	 * Returns a function transforming the second element of a 2-element tuple. The returned function
	 * returns {@code null} for {@code null} inputs.
	 * @param f Element transformation function.
	 * @return The requested function.
	 */
	public static <T0, T1, T> Function<Tuple2<T0, T1>, Tuple2<T0, T>> transformer2_1(final Function<T1, T> f) {
		return new Function<Tuple2<T0, T1>, Tuple2<T0, T>>() {
			public Tuple2<T0, T> apply(Tuple2<T0, T1> from) {
				if (from == null) {
					return null;
				}
				return tuple(from.get0(), f.apply(from.get1()));
			}

			@Override
			public String toString() {
				return "transformer2_1";
			}
		};
	}

	/**
	 * Returns a function transforming the second element of a 3-element tuple. The returned function
	 * returns {@code null} for {@code null} inputs.
	 * @param f Element transformation function.
	 * @return The requested function.
	 */
	public static <T0, T1, T2, T> Function<Tuple3<T0, T1, T2>, Tuple3<T0, T, T2>> transformer3_1(final Function<T1, T> f) {
		return new Function<Tuple3<T0, T1, T2>, Tuple3<T0, T, T2>>() {
			public Tuple3<T0, T, T2> apply(Tuple3<T0, T1, T2> from) {
				if (from == null) {
					return null;
				}
				return tuple(from.get0(), f.apply(from.get1()), from.get2());
			}

			@Override
			public String toString() {
				return "transformer3_1";
			}
		};
	}

	/**
	 * Returns a function transforming the third element of a 3-element tuple. The returned function
	 * returns {@code null} for {@code null} inputs.
	 * @param f Element transformation function.
	 * @return The requested function.
	 */
	public static <T0, T1, T2, T> Function<Tuple3<T0, T1, T2>, Tuple3<T0, T1, T>> transformer3_2(final Function<T2, T> f) {
		return new Function<Tuple3<T0, T1, T2>, Tuple3<T0, T1, T>>() {
			public Tuple3<T0, T1, T> apply(Tuple3<T0, T1, T2> from) {
				if (from == null) {
					return null;
				}
				return tuple(from.get0(), from.get1(), f.apply(from.get2()));
			}

			@Override
			public String toString() {
				return "transformer3_2";
			}
		};
	}

}
