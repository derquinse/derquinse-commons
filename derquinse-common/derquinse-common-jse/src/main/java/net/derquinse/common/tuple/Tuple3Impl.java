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
package net.derquinse.common.tuple;

import static net.derquinse.common.tuple.Tuples.tuple;

/**
 * Implementation of a 2-element tuple.
 * @author Andres Rodriguez
 * @param <T0> First element type.
 * @param <T1> Second element type.
 * @param <T2> Third element type.
 */
final class Tuple3Impl<T0, T1, T2> extends Tuple3<T0, T1, T2> {
	/**
	 * Constructor.
	 * @param e0 First element.
	 * @param e1 Second element.
	 * @param e2 Third element.
	 */
	Tuple3Impl(T0 e0, T1 e1, T2 e2) {
		super(e0, e1);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple3#curry(int)
	 */
	@Override
	public Tuple2<?, ?> curry(int index) {
		checkIndex(index);
		switch (index) {
		case 0:
			return curry0();
		case 1:
			return curry1();
		case 2:
			return curry2();
		default:
			throw new AssertionError();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple3#curry0()
	 */
	@Override
	public Tuple2<T1, T2> curry0() {
		return tuple(get1(), get2());
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple3#curry1()
	 */
	@Override
	public Tuple2<T0, T2> curry1() {
		return tuple(get0(), get2());
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple3#curry2()
	 */
	@Override
	public Tuple2<T0, T1> curry2() {
		return tuple(get0(), get1());
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple3#set(int, java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Tuple3<T0, T1, T2> set(int index, Object value) {
		checkIndex(index);
		switch (index) {
		case 0:
			return set0((T0) value);
		case 1:
			return set1((T1) value);
		case 2:
			return set2((T2) value);
		default:
			throw new AssertionError();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple3#set0(java.lang.Object)
	 */
	@Override
	public Tuple3<T0, T1, T2> set0(T0 value) {
		return tuple(value, get1(), get2());
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple3#set1(java.lang.Object)
	 */
	@Override
	public Tuple3<T0, T1, T2> set1(T1 value) {
		return tuple(get0(), value, get2());
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple3#set2(java.lang.Object)
	 */
	@Override
	public Tuple3<T0, T1, T2> set2(T2 value) {
		return tuple(get0(), get1(), value);
	}

}
