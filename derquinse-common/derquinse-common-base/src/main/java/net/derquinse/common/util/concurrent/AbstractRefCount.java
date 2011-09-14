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

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Abstract base for reference-counted objects. The max reference count is kept on a thread-safe
 * high watermark.
 * @author Andres Rodriguez
 * @param <T> Supplied object type.
 */
public abstract class AbstractRefCount<T> implements RefCount<T> {
	/** Supplied object. */
	private final T object;
	/** High watermark. */
	private final IntegerHighWaterMark watermark = IntegerHighWaterMark.create();

	/**
	 * Constructor.
	 * @param object Supplied object.
	 */
	public AbstractRefCount(T object) {
		this.object = checkNotNull(object);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.concurrent.RefCount#get()
	 */
	public final T get() {
		watermark.set(newRef());
		return object;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.concurrent.RefCount#getMaxCount()
	 */
	public int getMaxCount() {
		return watermark.get();
	}

	/**
	 * Adds a new reference to the supplied object.
	 * @return The new reference count.
	 * @throws IllegalStateException if the supplied object is no longer available.
	 */
	protected abstract int newRef();
}
