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

import static net.derquinse.common.product.Products.singleton;

/**
 * Pair implementation.
 * @author Andres Rodriguez
 * @param <T> Element type.
 */
class PairImpl<T> extends AbstractArrayPower<T> implements Pair<T> {
	/**
	 * Internal constructor for subclasses.
	 * @param arity Expected arity.
	 * @param e Elements. No defensive copy is made.
	 */
	PairImpl(int arity, T... e) {
		super(arity, e);
	}

	/**
	 * Constructor.
	 * @param e0 First element.
	 * @param e1 Second element.
	 */
	@SuppressWarnings("unchecked")
	PairImpl(T e0, T e1) {
		super(2, e0, e1);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Product1#get0()
	 */
	@Override
	public T get0() {
		return get(0);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Product2#get1()
	 */
	public T get1() {
		return get(1);
	}

	public Singleton<T> curry(int index) {
		checkIndex(index);
		return index == 0 ? curry0() : curry1();
	}

	public Singleton<T> curry0() {
		return singleton(get1());
	}

	public Singleton<T> curry1() {
		return singleton(get0());
	}

}
