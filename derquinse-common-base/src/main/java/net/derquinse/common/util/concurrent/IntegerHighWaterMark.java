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

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A thread-safe integer high watermark. Serialization pending.
 * @author Andres Rodriguez
 */
@SuppressWarnings("serial")
public final class IntegerHighWaterMark extends Number {
	/** Current value. */
	private final AtomicInteger atomic;

	/**
	 * Creates a new high watermark with the provided initial value.
	 * @param initialValue Inital value.
	 * @return The created object.
	 */
	public static final IntegerHighWaterMark create(int initialValue) {
		return new IntegerHighWaterMark(initialValue);
	}

	/**
	 * Creates a new high watermark with initial value 0.
	 * @return The created object.
	 */
	public static final IntegerHighWaterMark create() {
		return create(0);
	}

	private IntegerHighWaterMark(int initialValue) {
		this.atomic = new AtomicInteger(initialValue);
	}

	/**
	 * Returns the current value of the high watermark.
	 * @return The current value of the high watermark.
	 */
	public int get() {
		return atomic.get();
	}

	/**
	 * Sets the current value of the high watermark. The watermark is only changed if the provided
	 * value is greater that the current value.
	 * @param value Value to set.
	 */
	public void set(int value) {
		int current;
		do {
			current = atomic.get();
			if (current >= value) {
				return;
			}
		} while (!atomic.compareAndSet(current, value));
	}

	@Override
	public double doubleValue() {
		return atomic.doubleValue();
	}

	@Override
	public float floatValue() {
		return atomic.floatValue();
	}

	@Override
	public int intValue() {
		return atomic.intValue();
	}

	@Override
	public long longValue() {
		return atomic.longValue();
	}
}
