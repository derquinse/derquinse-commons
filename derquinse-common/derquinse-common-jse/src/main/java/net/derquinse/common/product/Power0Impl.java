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
 * Empty power.
 * @author Andres Rodriguez
 */
final class Power0Impl extends Power<Object> {
	static final Power0Impl POWER0 = new Power0Impl();

	/**
	 * Constructor.
	 */
	private Power0Impl() {
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
	 * @see net.derquinse.common.tuple.Tuple#set(int, java.lang.Object)
	 */
	@Override
	public Power<Object> set(int index, Object value) {
		checkIndex(index);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple#curry(int)
	 */
	@Override
	public Power<Object> curry(int index) {
		checkIndex(index);
		return null;
	}

	@Override
	public String toString() {
		return "()";
	}
}
