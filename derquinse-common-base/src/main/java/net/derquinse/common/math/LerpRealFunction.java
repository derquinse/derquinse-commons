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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Locale;

/**
 * Partial real function that uses linear interpolation based on table of values. The provided table
 * of values is an array with an even number of elements in the form {x1, f(x1), x2, f(x2), ..., xn,
 * f(xn)} with x1 ... xn in ascending order, and at least two pairs must be provided. The provided
 * table is not defensively copied so it MUST NOT be modified. No value outside the table is
 * extrapolated.
 * @author Andres Rodriguez
 */
class LerpRealFunction implements PartialRealFunction {
	private final double[] table;

	/**
	 * Constructor
	 * @param table Table of values.
	 * @throws IllegalArgumentException if the table is illegal.
	 */
	LerpRealFunction(double[] table) {
		checkNotNull(table, "A table of values must be provided.");
		checkArgument(table.length % 2 == 0, "The array must have an even number of elements");
		checkArgument(table.length >= 4, "At least two pairs must be provided");
		for (int i = 2; i < table.length - 1; i += 2) {
			checkArgument(table[i] > table[i - 2], "The domain values must be provided in ascending order");
		}
		this.table = table;
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.math.RealFunction#apply(double)
	 */
	public double apply(double input) {
		double x0 = table[0];
		checkArgument(input >= x0, "Value outside of the domain.");
		if (input == x0) {
			return table[1];
		}
		for (int i = 2; i < table.length - 1; i += 2) {
			final double x1 = table[i];
			if (x1 == input) {
				return table[i + 1];
			}
			if (x1 > input) {
				double y0 = table[i - 1];
				double m = (table[i + 1] - y0) / (x1 - x0);
				return y0 + m * (input - x0);
			}
			x0 = x1;
		}
		throw new IllegalArgumentException("Value outside of the domain.");
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.math.PartialRealFunction#isDefinedAt(double)
	 */
	public boolean isDefinedAt(double input) {
		return input >= table[0] && input <= table[table.length - 2];
	}

	@Override
	public String toString() {
		return String.format((Locale) null, "Linear interpolation for inputs in [%f, %f]", table[0],
				table[table.length - 1]);
	}
}
