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
import static net.derquinse.common.product.Products.power;
import static net.derquinse.common.product.Products.singleton;
import static net.derquinse.common.product.Products.tuple;

import org.testng.annotations.Test;

/**
 * Tests for 0-element products.
 * @author Andres Rodriguez
 */
public class Product0Test extends Base {
	private Tuple t;
	private Power<?> p;

	/**
	 * Create.
	 */
	@Test
	public void create() {
		t = notNull(tuple());
		p = notNull(power());
		check(t);
		check(p);
	}

	/**
	 * Equality.
	 */
	@Test(dependsOnMethods = "create")
	public void equals() {
		equality(t, p, tuple(), power());
		distinct(t, tuple(ONE), singleton(TWO), tuple(ONE, TWO), pair(TWO, ONE));
		distinct(p, tuple(ONE), singleton(TWO), tuple(ONE, TWO), pair(TWO, ONE));
	}

	/**
	 * Bad index.
	 */
	@Test(dependsOnMethods = "create")
	public void badIndex() {
		checkBadIndexes(-5, 5, t, p);
	}
}
