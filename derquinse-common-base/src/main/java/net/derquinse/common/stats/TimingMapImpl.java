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

import static com.google.common.base.Functions.compose;
import static com.google.common.base.Functions.constant;
import static com.google.common.collect.Maps.transformValues;
import static java.util.Collections.unmodifiableMap;
import static net.derquinse.common.stats.Timings.atomicGetter;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.MapMaker;

/**
 * Timing map implementation.
 * @author Andres Rodriguez
 * @param <K> The type of keys.
 */
final class TimingMapImpl<K> extends ForwardingMap<K, Timing> implements TimingMap<K> {
	/** Concurrent map. */
	private final ConcurrentMap<K, AtomicTiming> map;
	/** Unmodifiable view. */
	private final Map<K, Timing> view;

	/**
	 * Constructor.
	 * @param unit Time unit to use.
	 */
	TimingMapImpl(TimeUnit unit) {
		this.map = new MapMaker().makeComputingMap(compose(Timings.atomicCreator(), constant(unit)));
		this.view = unmodifiableMap(transformValues(map, atomicGetter()));
	}

	@Override
	protected Map<K, Timing> delegate() {
		return view;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.TimingMap#add(java.lang.Object, long,
	 * java.util.concurrent.TimeUnit)
	 */
	public Timing add(K key, long time, TimeUnit unit) {
		return map.get(key).add(time, unit).get();
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.TimingMap#add(java.lang.Object, java.lang.Number,
	 * java.util.concurrent.TimeUnit)
	 */
	public Timing add(K key, Number time, TimeUnit unit) {
		return map.get(key).add(time, unit).get();
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.TimingMap#add(java.lang.Object, java.lang.Number)
	 */
	public Timing add(K key, Number time) {
		return map.get(key).add(time).get();
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.common.collect.ForwardingMap#clear()
	 */
	@Override
	public void clear() {
		map.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.TimingMap#add(java.lang.Object, long)
	 */
	public Timing add(K key, long time) {
		return map.get(key).add(time).get();
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.common.collect.ForwardingObject#toString()
	 */
	@Override
	public String toString() {
		return map.toString();
	}
}
