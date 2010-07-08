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
package net.derquinse.common.collect;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Collection;

/**
 * Abstract class for hierarchy tests.
 * @author Andres Rodriguez
 */
public class AbstractHierarchyTest {
	static void self(Hierarchy<?> h) {
		if (h == null) {
			return;
		}
		assertTrue(h.equals(h));
	}

	static void equality(Hierarchy<?> h1, Hierarchy<?> h2) {
		if (h1 == null && h2 == null) {
			return;
		}
		assertTrue(h1 != null && h2 != null);
		self(h1);
		self(h2);
		assertTrue(h1.equals(h2));
		assertTrue(h2.equals(h1));
		assertTrue(h1.hashCode() == h2.hashCode());
	}

	static void empty(Collection<?> c) {
		assertNotNull(c);
		assertTrue(c.isEmpty());
		assertEquals(c.size(), 0);
		assertFalse(c.contains(new Object()));
	}

	static void empty(Hierarchy<?> h) {
		self(h);
		empty((Collection<?>) h);
		empty(h.elementSet());
		empty(h.getFirstLevel());
	}

	static void check(Collection<?> c, Object yes, Object no) {
		assertNotNull(c);
		assertTrue(c.contains(yes));
		assertFalse(c.contains(no));
	}

	static void check(Collection<?> c, int size, Object yes, Object no) {
		check(c, yes, no);
		assertEquals(c.size(), size);
	}

	static void check(Hierarchy<?> h, Object yes, Object no) {
		check((Collection<?>) h, yes, no);
		check(h.elementSet(), yes, no);
	}

	static void check(Hierarchy<?> h, int size, Object yes, Object no) {
		check((Collection<?>) h, size, yes, no);
		check(h.elementSet(), size, yes, no);
	}

}
