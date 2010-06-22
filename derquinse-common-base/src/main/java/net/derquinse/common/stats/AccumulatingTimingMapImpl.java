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

/**
 * Accumulating timing map implementation.
 * @author Andres Rodriguez
 * @param <K> The type of keys.
 */
final class AccumulatingTimingMapImpl<K> extends ForwardingTimingMap<K> implements AccumulatingTimingMap<K> {
	/** Timing map. */
	private final TimingMap<K> map;
	/** Accumulator. */
	private final AtomicTiming accumulator;

	/**
	 * Constructor.
	 * @param unit Time unit to use.
	 */
	AccumulatingTimingMapImpl(TimeUnit unit) {
		this.map = Timings.createMap(unit);
		this.accumulator = Timings.createAtomic(unit);
	}

	@Override
	protected TimingMap<K> delegate() {
		return map;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.ForwardingTimingMap#add(java.lang.Object, long,
	 * java.util.concurrent.TimeUnit)
	 */
	@Override
	public Timing add(K key, long time, TimeUnit unit) {
		accumulator.add(time, unit);
		return map.add(key, time, unit);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.ForwardingTimingMap#add(java.lang.Object, long)
	 */
	@Override
	public Timing add(K key, long time) {
		accumulator.add(time);
		return map.add(key, time);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.ForwardingTimingMap#add(java.lang.Object, java.lang.Number,
	 * java.util.concurrent.TimeUnit)
	 */
	@Override
	public Timing add(K key, Number time, TimeUnit unit) {
		accumulator.add(time, unit);
		return map.add(key, time, unit);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.ForwardingTimingMap#add(java.lang.Object, java.lang.Number)
	 */
	@Override
	public Timing add(K key, Number time) {
		accumulator.add(time);
		return map.add(key, time);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.AccumulatingTimingMap#getAccumulator()
	 */
	public Timing getAccumulator() {
		return accumulator.get();
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.common.collect.ForwardingObject#toString()
	 */
	@Override
	public String toString() {
		return String.format("(%s, %s)", accumulator, map);
	}
}
