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

import static net.derquinse.common.math.InverseErf.INSTANCE;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

/**
 * Tests for lerp real functions.
 * @author Andres Rodriguez
 */
public class InverseErfTest {
	/**
	 * Static method.
	 */
	@Test
	public void get() {
		assertTrue(INSTANCE == RealFunctions.inverseErf());
	}

	/**
	 * Values.
	 */
	@Test
	public void check() {
		assertTrue(INSTANCE.isDefinedAt(0.0));
		assertTrue(INSTANCE.isDefinedAt(0.45));
		assertTrue(INSTANCE.isDefinedAt(-0.45));
		assertFalse(INSTANCE.isDefinedAt(-2));
		assertFalse(INSTANCE.isDefinedAt(-1));
		assertFalse(INSTANCE.isDefinedAt(1));
		assertFalse(INSTANCE.isDefinedAt(2));
		for (double x = -2.0; x <= 2.0; x += 0.01) {
			final boolean expected = INSTANCE.isDefinedAt(x);
			boolean actual = false;
			try {
				double y = INSTANCE.apply(x);
				assertEquals(-INSTANCE.apply(-x), y);
				actual = true;
			} catch (IllegalArgumentException e) {
			}
			assertEquals(actual, expected);
		}
	}
}
