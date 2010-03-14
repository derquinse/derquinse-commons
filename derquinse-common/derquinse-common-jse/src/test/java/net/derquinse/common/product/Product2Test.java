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

import static net.derquinse.common.product.Products.pair;
import static net.derquinse.common.product.Products.tuple;

import org.testng.annotations.Test;

/**
 * Tests for 2-element products.
 * @author Andres Rodriguez
 */
public class Product2Test extends Base {
	private Tuple2<Integer, Integer> t;
	private Pair<Integer> p;
	

	/**
	 * Create.
	 */
	@Test
	public void create() {
		t = notNull(tuple(ONE, TWO));
		p = notNull(pair(ONE, TWO));
		check(t, ONE, TWO);
		check(p, ONE, TWO);
	}

	/**
	 * Equality.
	 */
	@Test(dependsOnMethods = "create")
	public void equals() {
		equality(t, p, tuple(ONE, TWO), pair(ONE, TWO));
		distinct(t, tuple(ONE, BYE), pair(HI, BYE));
		distinct(p, tuple(ONE, BYE), pair(HI, BYE));
	}

	/**
	 * Bad index.
	 */
	@Test(dependsOnMethods = "create")
	public void badIndex() {
		checkBadIndexes(-5, -1, t, p);
		checkBadIndexes(2, 5, t, p);
	}
}
