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

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

/**
 * Tests for ImmutableHierarchy.
 * @author Andres Rodriguez
 */
public class ImmutableHierarchyMapTest extends AbstractHierarchyTest {
	/**
	 * Empty hierarchy.
	 */
	@Test
	public void empty() {
		empty(ImmutableHierarchyMap.of());
		empty(ImmutableHierarchyMap.of().keyHierarchy());
		empty(ImmutableHierarchyMap.of(ImmutableMap.<Integer, Integer> of(), ImmutableHierarchy.of()));
	}

	/**
	 * Null Map.
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void nullMap() {
		ImmutableHierarchyMap.of(null, create(1));
	}

	/**
	 * Null Hierarchy.
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void nullHierarchy() {
		ImmutableHierarchyMap.of(ImmutableMap.<Integer, Integer> of(), null);
	}

	/**
	 * Bad Arguments.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void bad1() {
		ImmutableHierarchyMap.of(ImmutableMap.<Integer, Integer> of(), create(1));
	}

	/**
	 * Bad Arguments.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void bad2() {
		ImmutableHierarchyMap.of(ImmutableMap.of(-1, -3), create(1));
	}

	private HierarchyMap<Integer, String> createHM(int n) {
		final Hierarchy<Integer> keys = create(n);
		return ImmutableHierarchyMap.of(createStringMap(keys), keys);
	}

	/**
	 * Consistency.
	 */
	@Test
	public void consistent() {
		for (int i = 0; i < 100; i++) {
			HierarchyMap<Integer, String> h = createHM(i);
			self(h);
			self(h.keyHierarchy());
			equality(h.keyHierarchy().elementSet(), h.keySet());
		}
	}

	private static void hmEquality(HierarchyMap<?, ?> hm1, HierarchyMap<?, ?> hm2) {
		equality(hm1, hm2);
		equality(hm1.keyHierarchy(), hm2.keyHierarchy());
		equality(hm1.keyHierarchy().elementSet(), hm2.keySet());
		equality(hm2.keyHierarchy().elementSet(), hm1.keySet());
	}

	/**
	 * Equality.
	 */
	@Test
	public void equality() {
		for (int i = 0; i < 100; i++) {
			final HierarchyMap<Integer, String> h1 = createHM(i);
			final HierarchyMap<Integer, String> h2 = createHM(i);
			final HierarchyMap<Integer, String> hf = new ForwardingHierarchyMap<Integer, String>() {
				@Override
				protected HierarchyMap<Integer, String> delegate() {
					return h1;
				}
			};
			hmEquality(h1, h2);
			hmEquality(h1, hf);
			hmEquality(h2, hf);
		}
	}

}
