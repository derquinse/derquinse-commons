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

import static net.derquinse.common.tuple.Tuples.tuple;

/**
 * Implementation of a 2-element tuple.
 * @author Andres Rodriguez
 * @param <T0> First element type.
 * @param <T1> Second element type.
 */
final class Tuple2Impl<T0, T1> extends Tuple2<T0, T1> {
	/**
	 * Constructor.
	 * @param e0 First element.
	 * @param e1 Second element.
	 */
	Tuple2Impl(T0 e0, T1 e1) {
		super(e0, e1);
	}

	/* (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple2#curry(int)
	 */
	@Override
	public Tuple1<?> curry(int index) {
		checkIndex(index);
		return index == 0 ? curry0() : curry1();
	}

	/* (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple2#curry0()
	 */
	@Override
	public Tuple1<T1> curry0() {
		return tuple(get1());
	}

	/* (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple2#curry1()
	 */
	@Override
	public Tuple1<T0> curry1() {
		return tuple(get0());
	}

	/* (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple2#set(int, java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Tuple2<T0, T1> set(int index, Object value) {
		checkIndex(index);
		return index == 0 ? set0((T0)value) : set1((T1)value);
	}

	/* (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple2#set0(java.lang.Object)
	 */
	@Override
	public Tuple2<T0, T1> set0(T0 value) {
		return tuple(value, get1());
	}

	/* (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple2#set1(java.lang.Object)
	 */
	@Override
	public Tuple2<T0, T1> set1(T1 value) {
		return tuple(get0(), value);
	}
	
	
}
