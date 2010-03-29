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

import static net.derquinse.common.product.Products.singleton;
import static net.derquinse.common.product.Products.tuple;

import net.derquinse.common.product.Tuple1;

import org.testng.annotations.Test;

/**
 * Tests for 1-element products.
 * @author Andres Rodriguez
 */
public class Product1Test extends Base {
	private Tuple1<Integer> t;
	private Singleton<Integer> p;

	/**
	 * Create.
	 */
	@Test
	public void create() {
		t = notNull(tuple(ONE));
		p = notNull(singleton(ONE));
		check(t, ONE);
		check(p, ONE);
	}

	/**
	 * Equality.
	 */
	@Test(dependsOnMethods = "create")
	public void equals() {
		equality(t, p, tuple(ONE), singleton(ONE));
		distinct(t, tuple(TWO), singleton(BYE));
		distinct(p, tuple(TWO), singleton(BYE));
	}

	/**
	 * Bad index.
	 */
	@Test(dependsOnMethods = "create")
	public void badIndex() {
		checkBadIndexes(-5, -1, t, p);
		checkBadIndexes(1, 5, t, p);
	}
}