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
package net.derquinse.common.util.concurrent;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.util.concurrent.Atomics.newReference;

import java.util.concurrent.atomic.AtomicReference;

import net.derquinse.common.base.IntegerWaterMark;

import com.google.common.base.Objects;

/**
 * An atomic IntegerWaterMark. Provide access to the same mutation methods in a thread-safe way.
 * @author Andres Rodriguez
 */
public final class AtomicIntegerWaterMark {
	/** Current value. */
	private final AtomicReference<IntegerWaterMark> ref;

	/**
	 * Creates a new high watermark with the provided initial value.
	 * @param initialValue Inital value.
	 * @return The created object.
	 */
	public static final AtomicIntegerWaterMark of(IntegerWaterMark initialValue) {
		return new AtomicIntegerWaterMark(initialValue);
	}

	/**
	 * Creates a new object with the provided initial value.
	 * @param initialValue Inital value.
	 * @return The created object.
	 */
	public static final AtomicIntegerWaterMark of(int initialValue) {
		return of(IntegerWaterMark.of(initialValue));
	}

	/**
	 * Creates a new object with initial value 0.
	 * @return The created object.
	 */
	public static final AtomicIntegerWaterMark of() {
		return of(0);
	}

	private AtomicIntegerWaterMark(IntegerWaterMark initialValue) {
		this.ref = newReference(checkNotNull(initialValue, "Null initial values not allowed"));
	}

	/**
	 * Returns the current value.
	 */
	public IntegerWaterMark get() {
		return ref.get();
	}

	/**
	 * Sets the current value.
	 * @param value Value to set.
	 * @return The value set.
	 */
	public IntegerWaterMark set(int value) {
		IntegerWaterMark current;
		IntegerWaterMark newValue;
		do {
			current = ref.get();
			newValue = current.set(value);
			if (current == newValue) {
				return current;
			}
		} while (!ref.compareAndSet(current, newValue));
		return newValue;
	}

	/**
	 * Adds a certain amount to the current value.
	 * @param value Value to add.
	 * @return The value set.
	 */
	public IntegerWaterMark add(int value) {
		IntegerWaterMark current;
		IntegerWaterMark newValue;
		do {
			current = ref.get();
			newValue = current.add(value);
		} while (!ref.compareAndSet(current, newValue));
		return newValue;
	}

	/**
	 * Adds one to the current value.
	 * @return The value set.
	 */
	public IntegerWaterMark inc() {
		return add(1);
	}

	/**
	 * Subtract one to the current value.
	 * @return The value set.
	 */
	public IntegerWaterMark dec() {
		return add(-1);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).addValue(ref.get()).toString();
	}
}
