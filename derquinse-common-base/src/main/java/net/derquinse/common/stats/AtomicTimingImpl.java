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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Atomic timing implementation.
 * @author Andres Rodriguez
 */
final class AtomicTimingImpl implements AtomicTiming {
	/** Atomic reference. */
	private final AtomicReference<Timing> ref;

	/**
	 * Constructor.
	 * @param unit Time unit.
	 */
	AtomicTimingImpl(TimeUnit unit) {
		this.ref = new AtomicReference<Timing>(Timings.create(unit));
	}

	/**
	 * Constructor.
	 * @param t Initial value.
	 */
	AtomicTimingImpl(Timing t) {
		this.ref = new AtomicReference<Timing>(checkNotNull(t, "An initial value must be provided"));
	}

	/*
	 * (non-Javadoc)
	 * @see com.google.common.base.Supplier#get()
	 */
	public Timing get() {
		return ref.get();
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.AtomicTiming#add(long)
	 */
	public AtomicTiming add(long time) {
		if (time >= 0) {
			Timing t;
			do {
				t = ref.get();
			} while (!ref.compareAndSet(t, t.add(time)));
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.AtomicTiming#add(long, java.util.concurrent.TimeUnit)
	 */
	public final AtomicTiming add(long time, TimeUnit unit) {
		if (time >= 0) {
			Timing t;
			do {
				t = ref.get();
			} while (!ref.compareAndSet(t, t.add(time, unit)));
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.AtomicTiming#add(java.lang.Number)
	 */
	public final AtomicTiming add(Number time) {
		if (time != null) {
			add(time.longValue());
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.AtomicTiming#add(java.lang.Number, java.util.concurrent.TimeUnit)
	 */
	public final AtomicTiming add(Number time, TimeUnit unit) {
		if (time != null) {
			add(time.longValue(), unit);
		}
		return this;
	}

	@Override
	public String toString() {
		return get().toString();
	}

}
