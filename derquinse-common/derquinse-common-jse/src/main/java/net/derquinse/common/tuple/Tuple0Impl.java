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

/**
 * Empty tuple.
 * @author Andres Rodriguez
 */
final class Tuple0Impl extends Tuple {
	static final Tuple0Impl TUPLE0 = new Tuple0Impl();

	/**
	 * Constructor.
	 */
	private Tuple0Impl() {
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple#arity()
	 */
	public int arity() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple#get(int)
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
	public Tuple set(int index, Object value) {
		checkIndex(index);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.tuple.Tuple#curry(int)
	 */
	@Override
	public Tuple curry(int index) {
		checkIndex(index);
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		return TUPLE0 == obj;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return "()";
	}
}
