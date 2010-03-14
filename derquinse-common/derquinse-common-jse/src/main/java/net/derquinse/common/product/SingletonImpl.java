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

import static net.derquinse.common.product.Products.power;

/**
 * Implementation of a 1-element cartesian power.
 * @author Andres Rodriguez
 * @param <T> Element type.
 */
final class SingletonImpl<T> extends AbstractPower<T> implements Singleton<T> {
	/** Element. */
	private final T element;

	/**
	 * Constructor.
	 * @param element Element.
	 */
	SingletonImpl(T element) {
		this.element = element;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Product#arity()
	 */
	public int arity() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.product.Product1#get0()
	 */
	public T get0() {
		return element;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.product.Product#get(int)
	 */
	public T get(int index) {
		checkIndex(index);
		return element;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.product.Singleton#curry0()
	 */
	public Power<T> curry0() {
		return power();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.product.Power#curry(int)
	 */
	public Power<T> curry(int index) {
		checkIndex(index);
		return power();
	}
}
