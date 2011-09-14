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

import com.google.common.annotations.Beta;
import com.google.common.base.Supplier;

/**
 * Simple interface for a supplier of a single object that is reference-counted.
 * @author Andres Rodriguez
 * @param <T> Supplied object type.
 */
@Beta
public interface RefCount<T> extends Supplier<T> {
	/**
	 * Returns the supplied object, incrementing the reference count by one.
	 * @return The supplied object.
	 * @throws IllegalStateException if the supplied object is no longer available.
	 */
	T get();

	/**
	 * Notifies that the supplied object is no longer needed, decrementing the reference count by one.
	 * @throws IllegalStateException if the reference count is already zero.
	 */
	void dispose();

	/**
	 * Returns the current reference count.
	 * @return The current reference count.
	 */
	int getCount();

	/**
	 * Returns the maximum reference count.
	 * @return The maximum reference count.
	 */
	int getMaxCount();
}
