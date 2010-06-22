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

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A concurrent timings map. All the map-modifying operations except add and clear MUST throw
 * UnsupportedOperationException
 * @author Andres Rodriguez
 * @param <K> The type of keys.
 */
public interface TimingMap<K> extends Map<K, Timing> {

	/**
	 * Adds a new measure. If the argument is less than zero it is ignored.
	 * @param key Timing key.
	 * @param time Elapsed time.
	 * @return The timing after the update.
	 */
	Timing add(K key, long time);

	/**
	 * Adds a new measure. If the argument is less than zero it is ignored.
	 * @param key Timing key.
	 * @param time Elapsed time.
	 * @param unit Time unit of the argument.
	 * @return The timing after the update.
	 */
	Timing add(K key, long time, TimeUnit unit);

	/**
	 * Adds a new measure. If the argument is {@code null} or less than zero it is ignored.
	 * @param key Timing key.
	 * @param time Elapsed time.
	 * @return The timing after the update.
	 */
	Timing add(K key, Number time);

	/**
	 * Adds a new measure. If the argument is {@code null} or less than zero it is ignored.
	 * @param key Timing key.
	 * @param time Elapsed time.
	 * @param unit Time unit of the argument.
	 * @return The timing after the update.
	 */
	Timing add(K key, Number time, TimeUnit unit);
}
