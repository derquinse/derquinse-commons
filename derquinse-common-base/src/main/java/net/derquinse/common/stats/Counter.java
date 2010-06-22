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
package net.derquinse.common.stats;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.concurrent.atomic.AtomicLong;

import com.google.common.base.Function;

/**
 * A thread safe counter.
 * @author Andres Rodriguez
 */
public final class Counter implements Counting {
	/**
	 * Returns the counter creation function.
	 * @return The counter creation function.
	 */
	static Function<Long, Counter> creator() {
		return Creator.INSTANCE;
	}

	/**
	 * Creates a new counter.
	 * @param initialValue Initial value.
	 * @return A new counter.
	 * @throws IllegalArgumentException if the argument is less than 0.
	 */
	public static Counter create(long initialValue) {
		checkArgument(initialValue >= 0, "The initial value %d is not >= 0", initialValue);
		return new Counter(initialValue);
	}

	/**
	 * Creates a new counter with an initial value of 0.
	 * @return A new counter.
	 */
	public static Counter create() {
		return create(0L);
	}

	/** Counter. */
	private final AtomicLong count;

	/**
	 * Constructor.
	 * @param initialValue Initial value.
	 */
	private Counter(long initialValue) {
		count = new AtomicLong(initialValue);
	}

	/**
	 * Returns the current value.
	 * @return The current value.
	 */
	public long getCount() {
		return count.get();
	}

	/**
	 * Adds a value to the current.
	 * @param delta Value to add (>=0)
	 * @return The updated value.
	 * @throws IllegalArgumentException if the argument < 0
	 */
	public long add(long delta) {
		checkArgument(delta >= 0, "The argument %d should be >=0", delta);
		return delta == 0 ? count.get() : count.addAndGet(delta);
	}

	/**
	 * Increment the counter.
	 * @return The updated value.
	 */
	public long add() {
		return count.incrementAndGet();
	}

	/**
	 * Resets the counter to 0.
	 */
	public void reset() {
		count.set(0L);
	}

	private enum Creator implements Function<Long, Counter> {
		INSTANCE;

		public Counter apply(Long from) {
			return create(from != null ? from.longValue() : 0L);
		}
	}

}
