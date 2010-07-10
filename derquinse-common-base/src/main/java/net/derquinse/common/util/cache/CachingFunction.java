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
package net.derquinse.common.util.cache;

import net.derquinse.common.stats.Timing;

import com.google.common.base.Function;

/**
 * Caching functions interface.
 * @author Andres Rodriguez
 * @param <F> The type of the function input.
 * @param <T> The type of the function output.
 */
public interface CachingFunction<F, T> extends Function<F, T> {
	/**
	 * Returns the number of requests made.
	 * @return The number of requests.
	 */
	long getRequested();

	/**
	 * Returns the number of requests that had to be computed.
	 * @return The number of computed requests.
	 */
	long getComputed();

	/**
	 * Returns the percentage of hits that were not computed.
	 * @return The percentage of hits that were not computed.
	 */
	double getHitRatio();

	/**
	 * Returns the request timings.
	 * @return The request timing.
	 */
	Timing getRequestTiming();

	/**
	 * Returns the computation timing.
	 * @return The computation timing.
	 */
	Timing getComputationTiming();
}
