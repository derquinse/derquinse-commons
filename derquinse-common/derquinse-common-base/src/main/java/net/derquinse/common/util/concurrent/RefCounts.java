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
package net.derquinse.common.util.concurrent;

/**
 * Reference counted objects helper class.
 * @author Andres Rodriguez
 */
public final class RefCounts {
	/** Not instantiable. */
	private RefCounts() {
		throw new AssertionError();
	}

	/**
	 * Returns a reference-counted object. The reference count is kept in a thread-safe way.
	 * @param object Object to supply.
	 * @return The requested supplier.
	 * @throws NullPointerException if the argument is {@code null}.
	 */
	public <T> RefCount<T> of(T object) {
		return new SimpleRefCount<T>(object);
	}

	/**
	 * Returns stoppable reference-counted object. The reference count is kept in a thread-safe way.
	 * @param object Object to supply.
	 * @return The requested supplier.
	 * @throws NullPointerException if the argument is {@code null}.
	 */
	public <T> StoppableRefCount<T> stoppable(T object) {
		return new StoppableRefCountImpl<T>(object);
	}

}
