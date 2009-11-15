/*
 * Copyright 2009 the original author or authors.
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
package net.derquinse.common.tuple;

/**
 * Helper functions to operate on tuples.
 * @author Andres Rodriguez
 */
public final class Tuples {
	/** Not instantiable. */
	private Tuples() {
		throw new AssertionError();
	}

	/**
	 * First element extractor.
	 * @param t Tuple.
	 * @return The first element or {@code null} if the argument is {@code null}
	 *         .
	 */
	public static <T0> T0 get0(Tuple1<T0> t) {
		if (t == null) {
			return null;
		}
		return t.get0();
	}

	/**
	 * Second element extractor.
	 * @param t Tuple.
	 * @return The second element or {@code null} if the argument is {@code
	 *         null}.
	 */
	public static <T0, T1> T1 get1(Tuple2<T0, T1> t) {
		if (t == null) {
			return null;
		}
		return t.get1();
	}

	/**
	 * Third element extractor.
	 * @param t Tuple.
	 * @return The third element or {@code null} if the argument is {@code null}
	 *         .
	 */
	public static <T0, T1, T2> T2 get2(Tuple3<T0, T1, T2> t) {
		if (t == null) {
			return null;
		}
		return t.get2();
	}

	/**
	 * Returns the empty tuple.
	 * @return The empty tuple.
	 */
	public static Tuple tuple() {
		return Tuple0Impl.TUPLE0;
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
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple3(Tuple2<T0, T1> t0_1, Tuple1<T2> t2) {
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
	public static <T0, T1, T2> Tuple3<T0, T1, T2> tuple3(Tuple1<T0> t0, Tuple2<T1, T2> t1_2) {
		return tuple(get0(t0), t1_2);
	}

}
