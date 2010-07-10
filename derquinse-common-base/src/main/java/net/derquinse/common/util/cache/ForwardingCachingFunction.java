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

import net.derquinse.common.base.ForwardingFunction;
import net.derquinse.common.stats.Timing;

/**
 * Caching functions interface.
 * @author Andres Rodriguez
 * @param <F> The type of the function input.
 * @param <T> The type of the function output.
 */
public abstract class ForwardingCachingFunction<F, T> extends ForwardingFunction<F, T> implements CachingFunction<F, T> {
	/** Sole constructor. */
	protected ForwardingCachingFunction() {
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.common.collect.ForwardingObject#delegate()
	 */
	protected abstract CachingFunction<F, T> delegate();

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.cache.CachingFunction#getComputed()
	 */
	public long getComputed() {
		return delegate().getComputed();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.cache.CachingFunction#getHitRatio()
	 */
	public double getHitRatio() {
		return delegate().getHitRatio();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.cache.CachingFunction#getRequested()
	 */
	public long getRequested() {
		return delegate().getRequested();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.cache.CachingFunction#getComputationTiming()
	 */
	public Timing getComputationTiming() {
		return delegate().getComputationTiming();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.cache.CachingFunction#getRequestTiming()
	 */
	public Timing getRequestTiming() {
		return delegate().getRequestTiming();
	}

}
