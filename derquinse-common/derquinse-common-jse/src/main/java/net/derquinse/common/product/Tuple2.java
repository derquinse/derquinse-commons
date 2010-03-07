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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Class for a 2-element tuple.
 * @author Andres Rodriguez
 * @param <T0> First element type.
 * @param <T1> Second element type.
 */
public abstract class Tuple2<T0, T1> extends Tuple1<T0> implements Product2<T0, T1> {
	/** Elements. */
	private final Object[] array;

	/**
	 * Internal constructor for subclasses.
	 * @param e Elements. No defensive copy is made.
	 */
	Tuple2(Object... e) {
		this.array = checkNotNull(e, "No elements provided");
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
	 * @see net.derquinse.common.tuple.Product#get(int)
	 */
	public Object get(int index) {
		checkIndex(index);
		return array[index];
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Product1#get0()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T0 get0() {
		return (T0) array[0];
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Product2#get1()
	 */
	@SuppressWarnings("unchecked")
	public T1 get1() {
		return (T1) array[1];
	}

	/**
	 * Creates a new tuple with the same members as this except the one provided.
	 * @param index The member to replace index.
	 * @param value Value of the member to replace.
	 * @return The new tuple.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 * @throws ClassCastException if the provided member is of an invalid type.
	 */
	public abstract Tuple2<T0, T1> set(int index, Object value);

	/**
	 * Creates a new tuple with the same members as this except the first one.
	 * @param value Value of the first member.
	 * @return The new tuple.
	 */
	public abstract Tuple2<T0, T1> set0(T0 value);

	/**
	 * Creates a new tuple with the same members as this except the second one.
	 * @param value Value of the second member.
	 * @return The new tuple.
	 */
	public abstract Tuple2<T0, T1> set1(T1 value);

	/**
	 * Creates a new tuple with the same members as this except the one with the provided index, which
	 * is removed.
	 * @param index The member to remove index.
	 * @return The new tuple.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	public abstract Tuple1<?> curry(int index);

	/**
	 * Creates a new tuple with the same members as this except the first one, which is removed.
	 * @return The new tuple.
	 */
	public abstract Tuple1<T1> curry0();

	/**
	 * Creates a new tuple with the same members as this except the second one, which is removed.
	 * @return The new tuple.
	 */
	public abstract Tuple1<T0> curry1();

}
