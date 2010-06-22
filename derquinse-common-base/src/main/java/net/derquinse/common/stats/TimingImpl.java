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

import com.google.common.base.Objects;

/**
 * Timing measurements implementation.
 * @author Andres Rodriguez
 */
final class TimingImpl extends ForwardingLongPopulation implements Timing {
	/** Time unit. */
	private final TimeUnit unit;
	/** Base population. */
	private final LongPopulation population;

	/**
	 * Initializing Constructor.
	 * @param unit Time unit.
	 */
	TimingImpl(TimeUnit unit) {
		this.unit = checkNotNull(unit, "A time unit must be provided");
		this.population = Populations.ofLong();
	}

	/**
	 * Incremental constructor.
	 * @param t Timing.
	 * @param time Duration to add.
	 */
	private TimingImpl(TimingImpl t, long time) {
		this.unit = t.unit;
		this.population = t.population.add(time);
	}

	@Override
	protected LongPopulation delegate() {
		return population;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.Timing#getTimeUnit()
	 */
	public final TimeUnit getTimeUnit() {
		return unit;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.ForwardingLongPopulation#add(long)
	 */
	public Timing add(long time) {
		return time < 0 ? this : new TimingImpl(this, time);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.Timing#add(long, java.util.concurrent.TimeUnit)
	 */
	public final Timing add(long time, TimeUnit unit) {
		return add(this.unit.convert(time, unit));
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.Timing#add(java.lang.Number)
	 */
	public final Timing add(Number time) {
		return time != null ? add(time.longValue()) : add(-1L);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.Timing#add(java.lang.Number, java.util.concurrent.TimeUnit)
	 */
	public final Timing add(Number time, TimeUnit unit) {
		return time != null ? add(time.longValue(), unit) : add(-1L);
	}

	@Override
	public String toString() {
		return String.format("%s:%s", unit, population);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(unit, population);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TimingImpl) {
			TimingImpl t = (TimingImpl) obj;
			return unit == t.unit && population.equals(t.population);
		}
		return false;
	}

}
