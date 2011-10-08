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

/**
 * An int value together with its hictoric maximum and minimum values.
 * @author Andres Rodriguez
 */
public final class IntegerWaterMark {
	/** Current value. */
	private final int current;
	/** Maximum value. */
	private final int max;
	/** Minimum value. */
	private final int min;

	/**
	 * Creates a new high watermark with the provided initial value.
	 * @param initialValue Inital value.
	 * @return The created object.
	 */
	public static final IntegerWaterMark of(int initialValue) {
		return new IntegerWaterMark(initialValue);
	}

	/**
	 * Creates a new high watermark with initial value 0.
	 * @return The created object.
	 */
	public static final IntegerWaterMark of() {
		return of(0);
	}

	/**
	 * Constructor for an initial value.
	 * @param initialValue Initial value.
	 */
	private IntegerWaterMark(int initialValue) {
		this.current = initialValue;
		this.min = initialValue;
		this.max = initialValue;
	}

	/**
	 * Constructor for a modified value.
	 * @param previous Previous value.
	 * @param value New current value.
	 */
	private IntegerWaterMark(IntegerWaterMark previous, int value) {
		this.current = value;
		this.min = Math.min(value, previous.min);
		this.max = Math.max(value, previous.max);
	}

	/**
	 * Returns the current value.
	 */
	public int get() {
		return current;
	}

	/**
	 * Returns the minimum value.
	 */
	public int getMin() {
		return min;
	}

	/**
	 * Returns the maximum value.
	 */
	public int getMax() {
		return max;
	}

	/**
	 * Sets the new current value.
	 * @param value Value to set.
	 * @return A new watermark with the provided current value.
	 */
	public IntegerWaterMark set(int value) {
		if (current == value) {
			return this;
		}
		return new IntegerWaterMark(this, value);
	}

	/**
	 * Adds a certain amount to the current value.
	 * @param value Value to add.
	 * @return A new watermark with the modified value.
	 */
	public IntegerWaterMark add(int value) {
		return set(current + value);
	}

	/**
	 * Adds one to the current value.
	 * @return A new watermark with the modified value.
	 */
	public IntegerWaterMark inc() {
		return add(1);
	}

	/**
	 * Subtract one to the current value.
	 * @return A new watermark with the modified value.
	 */
	public IntegerWaterMark dec() {
		return add(-1);
	}

	public int hashCode() {
		return ((527 + current) * 31 + min) * 31 + max;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof IntegerWaterMark) {
			IntegerWaterMark m = (IntegerWaterMark) obj;
			return current == m.current && min == m.min && max == m.max;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("current", current).add("min", min).add("max", max).toString();
	}

}
