/*
 * Copyright 2008-2009 the original author or authors.
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

import com.google.common.base.Objects;

/**
 * Implementation of a 1-element tuple.
 * @author Andres Rodriguez
 * @param <T0> Element type.
 */
final class Tuple1Impl<T0> extends Tuple1<T0> {
	/** Element. */
	private final T0 element;

	/**
	 * Constructor.
	 * @param element First element.
	 */
	Tuple1Impl(T0 element) {
		this.element = element;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple#arity()
	 */
	public int arity() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple1#get0()
	 */
	@Override
	public T0 get0() {
		return element;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.tuple.Tuple#get(int)
	 */
	public Object get(int index) {
		checkIndex(index);
		return element;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple1#set0(java.lang.Object)
	 */
	@Override
	public Tuple1<T0> set0(T0 value) {
		return tuple(value);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple1#set(int, java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Tuple1<T0> set(int index, Object value) {
		checkIndex(index);
		return set0((T0) value);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple1#curry0()
	 */
	@Override
	public Tuple curry0() {
		return tuple();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple#curry(int)
	 */
	@Override
	public Tuple curry(int index) {
		checkIndex(index);
		return tuple();
	}

	@Override
	public int hashCode() {
		return element != null ? element.hashCode() : 31;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Tuple1Impl<?>) {
			return Objects.equal(element, ((Tuple1Impl<?>) obj).element);
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("(%s)", element);
	}

}
