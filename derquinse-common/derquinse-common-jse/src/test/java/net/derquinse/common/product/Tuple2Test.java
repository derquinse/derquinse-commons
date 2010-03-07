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
package net.derquinse.common.product;

import static net.derquinse.common.product.Tuples.tuple;

import net.derquinse.common.product.Tuple2;

import org.testng.annotations.Test;

/**
 * Tests for 2-element tuples.
 * @author Andres Rodriguez
 */
public class Tuple2Test extends Base {
	private Tuple2<Integer, Integer> t2;

	/**
	 * Create.
	 */
	@Test
	public void create() {
		t2 = notNull(tuple(ONE, TWO));
		check(t2, ONE, TWO);
	}

	/**
	 * Equality.
	 */
	@Test(dependsOnMethods = "create")
	public void equals() {
		equality(t2);
		distinct(t2, tuple(ONE, BYE));
		equality(t2, tuple(ONE, TWO), tuple(ONE, TWO));
	}

	/**
	 * Bad index.
	 */
	@Test(expectedExceptions = IndexOutOfBoundsException.class, dependsOnMethods = "create")
	public void badIndex1() {
		t2.get(-1);
	}

	/**
	 * Bad index.
	 */
	@Test(expectedExceptions = IndexOutOfBoundsException.class, dependsOnMethods = "create")
	public void badIndex2() {
		t2.get(2);
	}

}
