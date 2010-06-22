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

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Locale;

import com.google.common.base.Objects;
import com.google.common.primitives.Doubles;

/**
 * Long basic population implementation.
 * @author Andres Rodriguez
 */
abstract class LongPopulationImpl implements LongPopulation {
	static final LongPopulation EMPTY = new Empty();

	/**
	 * Constructor.
	 */
	private LongPopulationImpl() {
	}

	@Override
	public String toString() {
		return String.format((Locale) null, "[n=%d, mu=%f, min=%d, max=%d, s=%f, s2=%f]", getCount(), getMean(), getMin(),
				getMax(), getSigma(), getVariance());
	}

	private static final class Empty extends LongPopulationImpl {
		/**
		 * Constructor.
		 */
		private Empty() {
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.Population#getMean()
		 */
		public double getMean() {
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.Counting#getCount()
		 */
		public long getCount() {
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.LongPopulation#add(long)
		 */
		public LongPopulation add(long e) {
			return new Singleton(e);
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.LongPopulation#getMax()
		 */
		public long getMax() {
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.LongPopulation#getMin()
		 */
		public long getMin() {
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.Population#getSigma()
		 */
		public double getSigma() {
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.Population#getVariance()
		 */
		public double getVariance() {
			return 0;
		}

		@Override
		public int hashCode() {
			return 17;
		}

		@Override
		public boolean equals(Object obj) {
			return this == obj;
		}
	}

	private static final class Singleton extends LongPopulationImpl {
		private final long element;

		/**
		 * Constructor.
		 * @param e Element.
		 */
		private Singleton(long e) {
			this.element = e;
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.Counting#getCount()
		 */
		public long getCount() {
			return 1;
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.LongPopulation#add(long)
		 */
		public LongPopulation add(long e) {
			return new Many(this, e);
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.LongPopulation#getMax()
		 */
		public long getMax() {
			return element;
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.LongPopulation#getMin()
		 */
		public long getMin() {
			return element;
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.Population#getMean()
		 */
		public double getMean() {
			return element;
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.Population#getSigma()
		 */
		public double getSigma() {
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.Population#getVariance()
		 */
		public double getVariance() {
			return 0;
		}

		@Override
		public int hashCode() {
			return Doubles.hashCode(element);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Singleton) {
				return element == ((Singleton) obj).element;
			}
			return false;
		}
	}

	private static final class Many extends LongPopulationImpl {
		/** Count */
		private final long n;
		/** Minimun. */
		private final long min;
		/** Maximun. */
		private final long max;
		/** Mean. */
		private final double mean;
		/** Sum of squares of differences from the (current) mean. */
		private final double m2;

		/**
		 * Constructor.
		 * @param base Base population.
		 * @param e Element to add.
		 * @param m2 Previous m2.
		 */
		private Many(LongPopulationImpl base, long e, double m2) {
			this.n = base.getCount() + 1;
			this.min = min(base.getMin(), e);
			this.max = max(base.getMax(), e);
			double mean0 = base.getMean();
			double delta = e - mean0;
			this.mean = mean0 + delta / this.n;
			this.m2 = m2 + delta * (e - this.mean);
		}

		/**
		 * Constructor.
		 * @param base Base population.
		 * @param e Element to add.
		 */
		private Many(Many base, long e) {
			this(base, e, base.m2);
		}

		/**
		 * Constructor.
		 * @param base Base population.
		 * @param e Element to add.
		 */
		private Many(Singleton base, long e) {
			this(base, e, 0);
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.LongPopulation#add(long)
		 */
		public LongPopulation add(long e) {
			return new Many(this, e);
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.LongPopulation#getMax()
		 */
		public final long getMax() {
			return max;
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.LongPopulation#getMin()
		 */
		public final long getMin() {
			return min;
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.Population#getMean()
		 */
		public double getMean() {
			return mean;
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.Counting#getCount()
		 */
		public final long getCount() {
			return n;
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.Population#getSigma()
		 */
		public double getSigma() {
			return Math.sqrt(m2 / n);
		}

		/*
		 * (non-Javadoc)
		 * @see net.sf.derquinsej.stats.Population#getVariance()
		 */
		public double getVariance() {
			return m2 / n;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(n, min, max, mean, m2);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Many) {
				final Many m = (Many) obj;
				return n == m.n && min == m.min && max == m.max && mean == m.mean && m2 == m.m2;
			}
			return false;
		}

	}

}
