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

/**
 * Forwarding class for Counting interface.
 * @author Andres Rodriguez
 */
public abstract class ForwardingPopulation extends ForwardingCounting implements Population {
	protected ForwardingPopulation() {
	}

	protected abstract Population delegate();

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.Population#getMean()
	 */
	public double getMean() {
		return delegate().getMean();
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.Population#getSigma()
	 */
	public double getSigma() {
		return delegate().getSigma();
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.stats.Population#getVariance()
	 */
	public double getVariance() {
		return delegate().getVariance();
	}

}
