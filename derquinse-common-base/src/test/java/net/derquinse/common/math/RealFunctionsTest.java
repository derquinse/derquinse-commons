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

import static net.derquinse.common.math.RealFunctions.abs;
import static net.derquinse.common.math.RealFunctions.toFunction;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.common.base.Function;

/**
 * Tests for RealFunctions.
 * @author Andres Rodriguez
 */
public class RealFunctionsTest {
	private Function<Double, Double> f;

	/**
	 * Real function.
	 */
	@Test
	public void real() {
		assertEquals(abs().apply(1.0), Math.abs(1.0));
		assertEquals(abs().apply(-1.0), Math.abs(-1.0));
	}

	/**
	 * toFunction.
	 */
	@Test
	public void testToFunction() {
		f = toFunction(abs(), 0.0);
		assertEquals(f.apply(1.0), Math.abs(1.0));
		assertEquals(f.apply(-1.0), Math.abs(-1.0));
		assertEquals(f.apply(0.0), Math.abs(0.0));
		assertEquals(f.apply(null), Math.abs(0.0));
	}

	/**
	 * toFunction (null not allowed).
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void testToFunctionNotNull() {
		toFunction(abs()).apply(null);
	}
}
