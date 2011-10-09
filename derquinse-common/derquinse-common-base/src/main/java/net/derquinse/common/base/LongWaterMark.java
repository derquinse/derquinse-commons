/*
 * Copyright (C) the original author or authors.
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
package net.derquinse.common.base;

import com.google.common.base.Objects;
import com.google.common.primitives.Longs;

/**
 * An long value together with its hictoric maximum and minimum values.
 * @author Andres Rodriguez
 */
public final class LongWaterMark {
	/** Current value. */
	private final long current;
	/** Maximum value. */
	private final long max;
	/** Minimum value. */
	private final long min;

	/**
	 * Creates a new object with the provided initial value.
	 * @param initialValue Inital value.
	 * @return The created object.
	 */
	public static final LongWaterMark of(long initialValue) {
		return new LongWaterMark(initialValue);
	}

	/**
	 * Creates a new object with initial value 0.
	 * @return The created object.
	 */
	public static final LongWaterMark of() {
		return of(0);
	}

	/**
	 * Constructor for an initial value.
	 * @param initialValue Initial value.
	 */
	private LongWaterMark(long initialValue) {
		this.current = initialValue;
		this.min = initialValue;
		this.max = initialValue;
	}

	/**
	 * Constructor for a modified value.
	 * @param previous Previous value.
	 * @param value New current value.
	 */
	private LongWaterMark(LongWaterMark previous, long value) {
		this.current = value;
		this.min = Math.min(value, previous.min);
		this.max = Math.max(value, previous.max);
	}

	/**
	 * Returns the current value.
	 */
	public long get() {
		return current;
	}

	/**
	 * Returns the minimum value.
	 */
	public long getMin() {
		return min;
	}

	/**
	 * Returns the maximum value.
	 */
	public long getMax() {
		return max;
	}

	/**
	 * Sets the new current value.
	 * @param value Value to set.
	 * @return A new watermark with the provided current value.
	 */
	public LongWaterMark set(long value) {
		if (current == value) {
			return this;
		}
		return new LongWaterMark(this, value);
	}

	/**
	 * Adds a certain amount to the current value.
	 * @param value Value to add.
	 * @return A new watermark with the modified value.
	 */
	public LongWaterMark add(long value) {
		return set(current + value);
	}

	/**
	 * Adds one to the current value.
	 * @return A new watermark with the modified value.
	 */
	public LongWaterMark inc() {
		return add(1);
	}

	/**
	 * Subtract one to the current value.
	 * @return A new watermark with the modified value.
	 */
	public LongWaterMark dec() {
		return add(-1);
	}

	public int hashCode() {
		return ((527 + Longs.hashCode(current)) * 31 + Longs.hashCode(min)) * 31 + Longs.hashCode(max);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof LongWaterMark) {
			LongWaterMark m = (LongWaterMark) obj;
			return current == m.current && min == m.min && max == m.max;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("current", current).add("min", min).add("max", max).toString();
	}

}
