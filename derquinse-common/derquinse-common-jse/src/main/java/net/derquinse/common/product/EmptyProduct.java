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

import java.util.Iterator;

/**
 * Empty tuple.
 * @author Andres Rodriguez
 */
final class EmptyProduct extends AbstractProduct implements Power<Object> {
	static final EmptyProduct INSTANCE = new EmptyProduct();

	/**
	 * Constructor.
	 */
	private EmptyProduct() {
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Product#arity()
	 */
	public int arity() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Product#get(int)
	 */
	public Object get(int index) {
		checkIndex(index);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple#curry(int)
	 */
	public EmptyProduct curry(int index) {
		checkIndex(index);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<Object> iterator() {
		return new PowerIterator<Object>(this);
	}

	@Override
	public String toString() {
		return "()";
	}

}
