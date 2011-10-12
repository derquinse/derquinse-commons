/*
 * Copyright (C) the original author or authors.
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

import static com.google.common.base.Preconditions.checkArgument;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import net.derquinse.common.collect.ImmutableHierarchy.Builder;

import com.google.common.collect.Maps;

/**
 * Abstract class for hierarchy tests.
 * @author Andres Rodriguez
 */
public class AbstractHierarchyTest {
	static void self(Object o) {
		if (o == null) {
			return;
		}
		assertTrue(o.equals(o));
		assertFalse(o.equals(null));
	}

	static void equality(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return;
		}
		assertTrue(o1 != null && o2 != null);
		self(o1);
		self(o2);
		assertTrue(o1.equals(o2));
		assertTrue(o2.equals(o1));
		assertTrue(o1.hashCode() == o2.hashCode());
	}

	static void empty(Collection<?> c) {
		assertNotNull(c);
		assertTrue(c.isEmpty());
		assertEquals(c.size(), 0);
		assertFalse(c.contains(new Object()));
	}

	static void empty(Map<?, ?> m) {
		assertNotNull(m);
		assertTrue(m.isEmpty());
		assertEquals(m.size(), 0);
		assertFalse(m.containsKey(new Object()));
		assertFalse(m.containsValue(new Object()));
		empty(m.keySet());
		empty(m.values());
	}

	static void empty(Hierarchy<?> h) {
		self(h);
		assertTrue(h.isEmpty());
		assertEquals(h.size(), 0);
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
	}

	static void check(Hierarchy<?> h, Object yes, Object no) {
		check((Collection<?>) h, yes, no);
		check(h.elementSet(), yes, no);
	}

	static <E> void check(Hierarchy<E> h, int size, E yes, E no) {
		assertEquals(h.size(), size);
		assertTrue(h.contains(yes));
		assertFalse(h.contains(no));
		check(h.elementSet(), size, yes, no);
	}

	static Hierarchy<Integer> create(int n) {
		checkArgument(n >= 0);
		int d = 10;
		while (d <= n) {
			d *= 10;
		}
		Builder<Integer> b = ImmutableHierarchy.builder();
		for (int i = 1; i <= n; i++) {
			b.add(null, i);
			for (int j = 1; j <= n; j++) {
				b.add(i, i * d + j);
			}
		}
		return b.get();
	}

	static <K> Map<K, String> createStringMap(Iterable<K> keys) {
		final Map<K, String> map = Maps.newHashMap();
		for (K i : keys) {
			map.put(i, i.toString());
		}
		return map;
	}

}
