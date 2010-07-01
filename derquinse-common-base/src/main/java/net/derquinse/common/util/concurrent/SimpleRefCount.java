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

import static com.google.common.base.Preconditions.checkState;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple thread-safe reference-counted object.
 * @author Andres Rodriguez
 * @param <T> Supplied object type.
 */
final class SimpleRefCount<T> extends AbstractRefCount<T> {
	/** Reference count. */
	private final AtomicInteger count = new AtomicInteger();

	/**
	 * Constructor.
	 * @param object Supplied object.
	 */
	SimpleRefCount(T object) {
		super(object);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.concurrent.RefCount#getCount()
	 */
	public int getCount() {
		return count.get();
	}

	public void dispose() {
		int current;
		do {
			current = count.get();
			checkState(current > 0);
		} while (!count.compareAndSet(current, current - 1));
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.concurrent.AbstractRefCount#newRef()
	 */
	@Override
	protected int newRef() {
		return count.incrementAndGet();
	}

}
