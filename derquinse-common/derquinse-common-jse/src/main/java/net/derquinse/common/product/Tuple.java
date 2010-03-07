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

/**
 * Base class for tuples. For tuples to obey {@code equals} and {@code hashCode} contracts, it is
 * strongly recommended to use only immutable objects as tuple members.
 * @author Andres Rodriguez
 */
public abstract class Tuple extends AbstractProduct {
	/** Default constructor. */
	Tuple() {
	}

	/**
	 * Creates a new tuple with the same members as this except the one provided.
	 * @param index The member to replace index.
	 * @param value Value of the member to replace.
	 * @return The new tuple.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 * @throws ClassCastException if the provided member is of an invalid type.
	 */
	public abstract Tuple set(int index, Object value);

	/**
	 * Creates a new tuple with the same members as this except the one with the provided index, which
	 * is removed.
	 * @param index The member to remove index.
	 * @return The new tuple.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	public abstract Tuple curry(int index);
}
