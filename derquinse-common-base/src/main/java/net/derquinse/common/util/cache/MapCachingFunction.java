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

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.System.nanoTime;
import static net.derquinse.common.stats.Timings.createAtomic;

import java.util.Locale;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import net.derquinse.common.stats.AtomicTiming;
import net.derquinse.common.stats.Timing;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;

/**
 * A simple caching function backed by a computing concurrent map.
 * @author Andres Rodriguez
 * @param <F> The type of the function input.
 * @param <T> The type of the function output.
 */
final class MapCachingFunction<F, T> implements CachingFunction<F, T> {
	/** Backing map. */
	private final ConcurrentMap<F, T> map;
	/** Computer. */
	private final Computer computer;
	/** Request timing. */
	private final AtomicTiming request = createAtomic(TimeUnit.MILLISECONDS);
	/** Computation timing. */
	private final AtomicTiming computation = createAtomic(TimeUnit.MILLISECONDS);

	/**
	 * Constructor.
	 * @param maker Map maker to use.
	 * @param function Function to cache.
	 */
	MapCachingFunction(MapMaker maker, Function<? super F, ? extends T> function) {
		this.computer = new Computer(checkNotNull(function));
		this.map = checkNotNull(maker).makeComputingMap(computer);
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	public T apply(F from) {
		final long t0 = nanoTime();
		final T value = map.get(from);
		request.add((nanoTime() - t0) / 1000000);
		return value;
	};

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.cache.CachingFunction#getRequested()
	 */
	public long getRequested() {
		return request.get().getCount();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.cache.CachingFunction#getComputed()
	 */
	public long getComputed() {
		return computation.get().getCount();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.cache.CachingFunction#getHitRatio()
	 */
	public double getHitRatio() {
		final long r = getRequested();
		return (r == 0) ? 0.0 : ((double) (r - getComputed()) * 100) / r;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.cache.CachingFunction#getComputationTiming()
	 */
	public Timing getComputationTiming() {
		return computation.get();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.cache.CachingFunction#getRequestTiming()
	 */
	public Timing getRequestTiming() {
		return request.get();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format((Locale) null, "Cached Function [%s]: %f %% HR, R:%s, C:%s", computer.function.toString(),
				getHitRatio(), getRequestTiming(), getComputationTiming());
	}

	@Override
	public int hashCode() {
		return computer.function.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof MapCachingFunction<?, ?>) {
			return computer.function.equals(((MapCachingFunction<?, ?>) obj).computer.function);
		}
		return false;
	}

	/**
	 * Map entry computer function.
	 * @param <F> The type of the function input.
	 * @param <T> The type of the function output.
	 */
	private class Computer implements Function<F, T> {
		/** Function to apply. */
		private final Function<? super F, ? extends T> function;

		/**
		 * Constructor.
		 * @param function Function to apply.
		 */
		private Computer(Function<? super F, ? extends T> function) {
			this.function = function;
		}

		public T apply(F from) {
			final long t0 = nanoTime();
			final T value = function.apply(from);
			computation.add((nanoTime() - t0) / 1000000);
			return value;
		}

	}

}
