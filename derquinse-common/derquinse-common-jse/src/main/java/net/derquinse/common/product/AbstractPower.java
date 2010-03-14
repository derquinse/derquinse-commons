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
 * Base class for cartesian powers.
 * @author Andres Rodriguez
 * @param <T> Element type.
 */
abstract class AbstractPower<T> extends AbstractProduct implements Power<T> {
	AbstractPower() {
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Product#asIterable()
	 */
	public final Iterable<? extends Object> asIterable() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.product.Product#productIterator()
	 */
	public Iterator<? extends Object> productIterator() {
		return iterator();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<T> iterator() {
		return new PowerIterator<T>(this);
	}
}
