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

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Function;

/**
 * Real functions helper class.
 * @author Andres Rodriguez
 */
public final class RealFunctions {
	/** Not instantiable. */
	private RealFunctions() {
		throw new AssertionError();
	}

	/**
	 * Transform a real function into a function that does not allow the null input.
	 * @param f Real function to tranform.
	 * @return The requested function.
	 * @throws NullPointerException if the argument is {@code null}.
	 */
	public static Function<Double, Double> toFunction(final RealFunction f) {
		checkNotNull(f, "A real function must be provided.");
		return new Function<Double, Double>() {
			public Double apply(Double from) {
				return f.apply(checkNotNull(from, "Null input not allowed").doubleValue());
			}

			@Override
			public String toString() {
				return f.toString();
			}
		};
	}

	/**
	 * Transform a real function into a function that allows the null input.
	 * @param f Real function to tranform.
	 * @param nullValue The function's output for the null input.
	 * @return The requested function.
	 * @throws NullPointerException if the argument is {@code null}.
	 */
	public static Function<Double, Double> toFunction(final RealFunction f, final Double nullValue) {
		checkNotNull(f, "A real function must be provided.");
		return new Function<Double, Double>() {
			public Double apply(Double from) {
				return from != null ? f.apply(from.doubleValue()) : nullValue;
			}

			@Override
			public String toString() {
				return f.toString();
			}
		};
	}

	/**
	 * Returns a partial real function that uses linear interpolation based on table of values. The
	 * provided table of values is an array with an even number of elements in the form {x1, f(x1),
	 * x2, f(x2), ..., xn, f(xn)} with x1 ... xn in ascending order, and at least two pairs must be
	 * provided. The provided table is not defensively copied so it MUST NOT be modified. No value
	 * outside the table is extrapolated.
	 * @return The requested function.
	 * @throws NullPointerException if the argument is {@code null}.
	 * @throws IllegalArgumentException if the table is illegal.
	 */
	public static PartialRealFunction lerp(double[] table) {
		return new LerpRealFunction(table);
	}

	/**
	 * Returns the inverse error function.
	 * @return The inverse error function.
	 */
	public static PartialRealFunction inverseErf() {
		return InverseErf.INSTANCE;
	}

	/**
	 * Applies the inverse error function to the argument.
	 * @return The result of the function application.
	 * @throws IllegalArgumentException if the argument is not in (-1, 1)
	 */
	public static double inverseErf(double input) {
		return InverseErf.INSTANCE.apply(input);
	}

	public static RealFunction abs() {
		return Real.ABS;
	}

	private enum Real implements RealFunction {
		ABS {
			public double apply(double input) {
				return Math.abs(input);
			}
		}
	}

}
