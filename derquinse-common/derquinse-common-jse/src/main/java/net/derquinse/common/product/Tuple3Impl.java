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

import static net.derquinse.common.product.Tuples.tuple;

/**
 * Implementation of a 3-element tuple.
 * @author Andres Rodriguez
 * @param <T0> First element type.
 * @param <T1> Second element type.
 * @param <T2> Third element type.
 */
class Tuple3Impl<T0, T1, T2> extends Tuple2Impl<T0, T1> implements Tuple3<T0, T1, T2> {
	/**
	 * Internal constructor for subclasses.
	 * @param arity Expected arity.
	 * @param e Elements. No defensive copy is made.
	 */
	Tuple3Impl(int arity, Object... e) {
		super(arity, e);
	}

	/**
	 * Constructor.
	 * @param e0 First element.
	 * @param e1 Second element.
	 * @param e2 Third element.
	 */
	Tuple3Impl(T0 e0, T1 e1, T2 e2) {
		super(3, e0, e1, e2);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Product3#get2()
	 */
	@SuppressWarnings("unchecked")
	public T2 get2() {
		return (T2) get(2);
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
}
