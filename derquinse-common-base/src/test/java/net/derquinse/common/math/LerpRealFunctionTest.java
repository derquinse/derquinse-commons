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

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * Tests for lerp real functions.
 * @author Andres Rodriguez
 */
public class LerpRealFunctionTest {
	private static final double[] OK = { 0.0, 0.0, 10.0, 20.0, 20.0, 120.0 };
	private LerpRealFunction ok;

	/**
	 * Create.
	 */
	@Test
	public void create() {
		ok = new LerpRealFunction(OK);
	}

	/**
	 * Check.
	 */
	@Test(dependsOnMethods = "create")
	public void check() {
		for (int i = 0; i < OK.length - 1; i += 2) {
			assertEquals(ok.apply(OK[i]), OK[i + 1]);
		}
		assertEquals(ok.apply(1.0), 2.0);
		assertEquals(ok.apply(3.0), 6.0);
		assertEquals(ok.apply(11.0), 30.0);
	}

	/**
	 * Bad value.
	 */
	@Test(dependsOnMethods = "create", expectedExceptions = IllegalArgumentException.class)
	public void value1() {
		ok.apply(-1.0);
	}

	/**
	 * Bad value.
	 */
	@Test(dependsOnMethods = "create", expectedExceptions = IllegalArgumentException.class)
	public void value2() {
		ok.apply(20.2);
	}

	/**
	 * Bad table.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void table1() {
		new LerpRealFunction(new double[] { 0.0, 1.0 });
	}

	/**
	 * Bad table.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void table2() {
		new LerpRealFunction(new double[] { 0.0, 1.0, 2.0 });
	}

	/**
	 * Bad table.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void table3() {
		new LerpRealFunction(new double[] { 0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 2.0, 7.0 });
	}

	/**
	 * toString.
	 */
	@Test
	public void string() {
		System.out.println(ok);
	}

}
