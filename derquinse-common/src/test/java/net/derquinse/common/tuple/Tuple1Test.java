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
package net.derquinse.common.tuple;

import static net.derquinse.common.tuple.Tuples.tuple;

import org.testng.annotations.Test;

/**
 * Tests for 1-element tuples.
 * @author Andres Rodriguez
 */
public class Tuple1Test extends Base {
	private Tuple1<Integer> t;

	/**
	 * Create.
	 */
	@Test
	public void create() {
		t = notNull(tuple(ONE));
	}

	/**
	 * Check.
	 */
	@Test(dependsOnMethods = "create")
	public void check() {
		check(t, ONE);
	}

	/**
	 * Equality.
	 */
	@Test(dependsOnMethods = "check")
	public void equals() {
		equality(t);
		distinct(t, tuple(TWO));
		equality(t, tuple(ONE), tuple(ONE));
	}

	/**
	 * Bad index.
	 */
	@Test(expectedExceptions = IndexOutOfBoundsException.class, dependsOnMethods = "create")
	public void badIndex1() {
		t.get(-1);
	}

	/**
	 * Bad index.
	 */
	@Test(expectedExceptions = IndexOutOfBoundsException.class, dependsOnMethods = "create")
	public void badIndex2() {
		t.get(2);
	}

}
