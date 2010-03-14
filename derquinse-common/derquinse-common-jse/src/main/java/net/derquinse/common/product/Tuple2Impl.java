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

import static net.derquinse.common.product.Products.tuple;

/**
 * Implementation of a 2-element tuple.
 * @author Andres Rodriguez
 * @param <T0> First element type.
 * @param <T1> Second element type.
 */
class Tuple2Impl<T0, T1> extends AbstractArrayTuple implements Tuple2<T0, T1> {
	/**
	 * Internal constructor for subclasses.
	 * @param arity Expected arity.
	 * @param e Elements. No defensive copy is made.
	 */
	Tuple2Impl(int arity, Object... e) {
		super(arity, e);
	}

	/**
	 * Constructor.
	 * @param e0 First element.
	 * @param e1 Second element.
	 */
	Tuple2Impl(T0 e0, T1 e1) {
		super(2, e0, e1);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Product1#get0()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public final T0 get0() {
		return (T0) get(0);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Product2#get1()
	 */
	@SuppressWarnings("unchecked")
	public final T1 get1() {
		return (T1) get(1);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple2#curry(int)
	 */
	@Override
	public Tuple1<?> curry(int index) {
		checkIndex(index);
		return index == 0 ? curry0() : curry1();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple2#curry0()
	 */
	@Override
	public Tuple1<T1> curry0() {
		return tuple(get1());
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple2#curry1()
	 */
	@Override
	public Tuple1<T0> curry1() {
		return tuple(get0());
	}
}
