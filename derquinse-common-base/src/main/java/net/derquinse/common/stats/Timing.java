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
 * Timing measurements.
 * A timing measurement is represented as a population of durations.
 * @author Andres Rodriguez
 */
public interface Timing extends LongPopulation {
	/**
	 * Returns the time unit used in the return values.
	 * @return The time unit used in the return values.
	 */
	TimeUnit getTimeUnit();

	/**
	 * Adds a new measure. If the argument is less than zero it is ignored.
	 * @param time Elapsed time.
	 * @return An updated timing or the same if the argument is less than zero.
	 */
	Timing add(long time);

	/**
	 * Adds a new measure. If the argument is less than zero it is ignored.
	 * @param time Elapsed time.
	 * @param unit Time unit of the argument.
	 * @return An updated timing or the same if the argument is less than zero.
	 */
	Timing add(long time, TimeUnit unit);

	/**
	 * Adds a new measure. If the argument is {@code null} or less than zero it
	 * is ignored.
	 * @param time Elapsed time.
	 * @return An updated timing or the same if the argument is {@code null} or
	 *         less than zero.
	 */
	Timing add(Number time);

	/**
	 * Adds a new measure. If the argument is {@code null} or less than zero it
	 * is ignored.
	 * @param time Elapsed time.
	 * @param unit Time unit of the argument.
	 * @return An updated timing or the same if the argument is {@code null} or
	 *         less than zero.
	 */
	Timing add(Number time, TimeUnit unit);

}
