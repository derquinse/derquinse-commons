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

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Supplier of a single object that is reference-counted and can be requested until it is stopped.
 * @author Andres Rodriguez
 * @param <T> Supplied object type.
 */
public interface StoppableRefCount<T> extends RefCount<T> {
	/**
	 * Returns whether the supplied object is still available.
	 * @return True if the supplied object is still available.
	 */
	boolean isAvailable();

	/**
	 * Returns whether the supplied object is still available or there are active references.
	 * @return True if the supplied object is still available or there are active references.
	 */
	boolean isRunning();

	/**
	 * Stop supplying the object. Calls to {@link #get() get} performed after calling this method will
	 * throw {@IllegalStateException}.
	 * @return A listenable future that will be set to {@code null} when the reference count reaches
	 *         0. Every call to this method returns the same future object.
	 */
	ListenableFuture<?> stop();
}
