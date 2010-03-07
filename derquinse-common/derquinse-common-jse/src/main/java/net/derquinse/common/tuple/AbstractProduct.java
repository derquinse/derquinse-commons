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

import java.util.Iterator;

import com.google.common.base.Joiner;

/**
 * Skeletal implementation for products.
 * @author Andres Rodriguez
 */
public abstract class AbstractProduct implements Product {
	/** Default constructor. */
	public AbstractProduct() {
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<Object> iterator() {
		return new ProductIterator(this);
	}

	/**
	 * Checks an index.
	 * @param index Index to check.
	 * @throws IndexOutOfBoundsException if index < 0 or index => arity.
	 */
	protected final void checkIndex(int index) {
		if (index < 0 || index >= arity()) {
			throw new IndexOutOfBoundsException(String.format("Requested element %d but product arity is %d", index, arity()));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Products.hashCode(this);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Product) {
			return Products.equal(this, (Product) obj);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append('(');
		Joiner.on(", ").useForNull("null").appendTo(sb, this);
		sb.append(')');
		return sb.toString();
	}
}
