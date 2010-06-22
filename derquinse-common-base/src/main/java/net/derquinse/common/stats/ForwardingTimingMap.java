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

import com.google.common.collect.ForwardingMap;

/**
 * Forwarding timing map.
 * @author Andres Rodriguez
 * @param <K> The type of keys.
 */
public abstract class ForwardingTimingMap<K> extends ForwardingMap<K, Timing> implements TimingMap<K> {
	/**
	 * Constructor.
	 */
	protected ForwardingTimingMap() {
	}

	@Override
	protected abstract TimingMap<K> delegate();

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.TimingMap#add(java.lang.Object, long,
	 * java.util.concurrent.TimeUnit)
	 */
	public Timing add(K key, long time, TimeUnit unit) {
		return delegate().add(key, time, unit);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.TimingMap#add(java.lang.Object, long)
	 */
	public Timing add(K key, long time) {
		return delegate().add(key, time);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.TimingMap#add(java.lang.Object, java.lang.Number,
	 * java.util.concurrent.TimeUnit)
	 */
	public Timing add(K key, Number time, TimeUnit unit) {
		return delegate().add(key, time, unit);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.TimingMap#add(java.lang.Object, java.lang.Number)
	 */
	public Timing add(K key, Number time) {
		return delegate().add(key, time);
	}

}
