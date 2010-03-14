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

import static net.derquinse.common.product.Products.power;
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
		equality(t);
		equality(t, tuple());
		equality(t, power());
		distinct(t, Tuples.tuple(ONE));
		equality(t, tuple(), tuple());
		equality(t, tuple(), power());
		equality(t, power(), power());
	}

	/**
	 * Bad index.
	 */
	@Test(dependsOnMethods = "create")
	public void badIndex() {
		checkBadIndexes(-5, 5, t, p);
	}

	/**
	 * Bad index.
	 */
	@Test(expectedExceptions = IndexOutOfBoundsException.class, dependsOnMethods = "create")
	public void badIndex2() {
		t.get(1);
	}

}
