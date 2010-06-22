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
package net.derquinse.common.math;

import java.util.Locale;

/**
 * Inverse error function.
 * @author Andres Rodriguez
 */
class InverseErf extends LerpRealFunction {
	private static final double[] TABLE = { 0.0, 0.0, //
			0.1000000, 0.088856, //
			0.2000000, 0.179143, //
			0.3000000, 0.272463, //
			0.4000000, 0.370887, //
			0.5000000, 0.476936, //
			0.6000000, 0.595116, //
			0.7000000, 0.732869, //
			0.8000000, 0.906194, //
			0.9000000, 1.163090, //
			0.9500000, 1.385900, //
			0.9800000, 1.644980, //
			0.9980000, 2.185120, //
			0.9998000, 2.629740, //
			0.9999800, 3.015730, //
			0.9999980, 3.361180, //
			0.9999998, 3.676490, //
			1.0000000, Double.MAX_VALUE };
	static final InverseErf INSTANCE = new InverseErf();

	/**
	 * Constructor
	 */
	private InverseErf() {
		super(TABLE);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.math.RealFunction#apply(double)
	 */
	public double apply(double input) {
		if (!isDefinedAt(input)) {
			throw new IllegalArgumentException(String.format((Locale) null, "InverseErf: %f outside (-1,1)", input));
		}
		return input >= 0 ? super.apply(input) : -super.apply(-input);
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.math.PartialRealFunction#isDefinedAt(double)
	 */
	public boolean isDefinedAt(double input) {
		return input > -1.0 && input < 1.0;
	}

	@Override
	public String toString() {
		return "Inverse Error";
	}
}
