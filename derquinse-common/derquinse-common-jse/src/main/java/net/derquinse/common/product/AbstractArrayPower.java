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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Bae class for array-based cartesian powers.
 * @author Andres Rodriguez
 * @param <T> Element type.
 */
abstract class AbstractArrayPower<T> extends AbstractPower<T> implements Power<T> {
	/** Elements. */
	private final T[] array;

	/**
	 * Internal constructor for subclasses.
	 * @param arity Expected arity.
	 * @param e Elements. No defensive copy is made.
	 */
	AbstractArrayPower(int arity, T... e) {
		this.array = checkNotNull(e, "No elements provided");
		checkArgument(arity == e.length, "Expected %d elements, provided %d", arity, e.length);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Product#arity()
	 */
	public final int arity() {
		return array.length;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.product.Product#get(int)
	 */
	public final T get(int index) {
		checkIndex(index);
		return array[index];
	}
}
