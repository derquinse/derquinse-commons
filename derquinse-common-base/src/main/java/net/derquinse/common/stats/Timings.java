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

import java.util.concurrent.TimeUnit;

import com.google.common.base.Function;

/**
 * Timings factory and helper methods.
 * @author Andres Rodriguez
 */
public final class Timings {
	/** Not instantiable. */
	private Timings() {
		throw new AssertionError();
	}

	/**
	 * Returns the timing creation function.
	 * @return The timing creation function.
	 */
	public static Function<TimeUnit, Timing> creator() {
		return Creator.INSTANCE;
	}

	/**
	 * Creates a new timing measurement.
	 * @param unit Time unit.
	 * @return A new timing measurement.
	 */
	public static Timing create(TimeUnit unit) {
		return new TimingImpl(unit);
	}

	/**
	 * Returns the atomic timing creation function.
	 * @return The atomic timing creation function.
	 */
	public static Function<TimeUnit, AtomicTiming> atomicCreator() {
		return AtomicCreator.INSTANCE;
	}

	/**
	 * Returns the atomic timing getter function.
	 * @return The atomic timing getter function.
	 */
	public static Function<AtomicTiming, Timing> atomicGetter() {
		return AtomicGetter.INSTANCE;
	}

	/**
	 * Creates a new atomic timing measurement.
	 * @param unit Time unit.
	 * @return A new atomic timing measurement.
	 */
	public static AtomicTiming createAtomic(TimeUnit unit) {
		return new AtomicTimingImpl(unit);
	}

	/**
	 * Creates a new atomic timing measurement.
	 * @param timing Initial value.
	 * @return A new atomic timing measurement.
	 */
	public static AtomicTiming createAtomic(Timing timing) {
		return new AtomicTimingImpl(timing);
	}

	/**
	 * Creates a new timing map.
	 * @param unit Time unit.
	 * @return A new timing map.
	 */
	public static <K> TimingMap<K> createMap(TimeUnit unit) {
		return new TimingMapImpl<K>(unit);
	}

	/**
	 * Creates a new accumulating timing map.
	 * @param unit Time unit.
	 * @return A new accumulating timing map.
	 */
	public static <K> AccumulatingTimingMap<K> createAccumulatingMap(TimeUnit unit) {
		return new AccumulatingTimingMapImpl<K>(unit);
	}

	private enum Creator implements Function<TimeUnit, Timing> {
		INSTANCE;

		public Timing apply(TimeUnit from) {
			return create(from);
		}
	}

	private enum AtomicCreator implements Function<TimeUnit, AtomicTiming> {
		INSTANCE;

		public AtomicTiming apply(TimeUnit from) {
			return createAtomic(from);
		}
	}

	private enum AtomicGetter implements Function<AtomicTiming, Timing> {
		INSTANCE;

		public Timing apply(AtomicTiming from) {
			return from.get();
		}
	}

}
