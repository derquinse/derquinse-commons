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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Base class for product tests.
 * @author Andres Rodriguez
 */
class Base {
	static final Integer ONE = Integer.valueOf(1);
	static final Integer TWO = Integer.valueOf(2);
	static final String HI = "hi";
	static final String BYE = "bye";

	static <T> T notNull(T obj) {
		assertNotNull(obj);
		return obj;
	}

	static <T extends Product> T check(T t, Object... objs) {
		assertEquals(t.arity(), objs.length);
		for (int i = 0; i < objs.length; i++) {
			assertEquals(t.get(i), objs[i]);
		}
		if (t instanceof Product1<?>) {
			assertEquals(((Product1<?>) t).get0(), objs[0]);
			if (t instanceof Product2<?, ?>) {
				assertEquals(((Product2<?, ?>) t).get1(), objs[1]);
				if (t instanceof Product3<?, ?, ?>) {
					assertEquals(((Product3<?, ?, ?>) t).get2(), objs[2]);
				}
			}
		}
		return t;
	}

	/**
	 * Equality 1.
	 */
	static void equality(Object one) {
		assertTrue(one.equals(one));
		assertFalse(one.equals(null));
		assertEquals(one.hashCode(), one.hashCode());
	}

	/**
	 * Distinct.
	 */
	static void distinct(Object one, Object other) {
		assertFalse(one.equals(other));
		assertFalse(other.equals(one));
	}

	/**
	 * Equality 3.
	 */
	static void equality(Object one, Object two) {
		assertEquals(one, two);
		assertEquals(two, one);
		assertEquals(one.hashCode(), two.hashCode());
	}

	/**
	 * Equality 3.
	 */
	static void equality(Object one, Object two, Object three) {
		equality(one, two);
		equality(three, two);
		equality(one, three);
	}

	/**
	 * Checks for a bad index.
	 * @param p Product to test.
	 * @param i Index to test.
	 */
	static void checkBadIndex(Product p, int i) {
		boolean ok = false;
		try {
			p.get(i);
		} catch (IndexOutOfBoundsException e) {
			ok = true;
		}
		assertTrue(ok, String.format("Index %d should have thrown IndexOutOfBoundsException for %s", i, p));
	}

	/**
	 * Checks for a bad index range.
	 * @param min Range start.
	 * @param max Range end.
	 * @param products Products to test.
	 */
	static void checkBadIndexes(int min, int max, Product... products) {
		assertTrue(max >= min);
		for (int i = min; i <= max; i++) {
			for (Product p : products) {
				checkBadIndex(p, i);
			}
		}
	}

}
