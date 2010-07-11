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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;

/**
 * Tests for ImmutableIndexedHierarchy.
 * @author Andres Rodriguez
 */
public class ImmutableIndexedHierarchyTest extends AbstractHierarchyTest {

	static <K, V> BiMap<K, V> createBiMap(Map<? extends K, ? extends V> map) {
		return ImmutableBiMap.<K, V> builder().putAll(map).build();
	}

	static <K> BiMap<K, String> createStringBiMap(Iterable<K> keys) {
		return createBiMap(createStringMap(keys));
	}

	/**
	 * Empty hierarchy.
	 */
	@Test
	public void empty() {
		empty(ImmutableIndexedHierarchy.of());
		empty(ImmutableIndexedHierarchy.of().asMap());
		empty(ImmutableIndexedHierarchy.of().inverse());
	}

	/**
	 * Null Map.
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void nullMap() {
		ImmutableIndexedHierarchy.of(null, create(1));
	}

	/**
	 * Null Hierarchy.
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void nullHierarchy() {
		ImmutableIndexedHierarchy.of(ImmutableBiMap.<Integer, Integer> of(), null);
	}

	/**
	 * Bad Arguments.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void bad1() {
		ImmutableIndexedHierarchy.of(ImmutableBiMap.<Integer, Integer> of(), create(1));
	}

	/**
	 * Bad Arguments.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void bad2() {
		ImmutableIndexedHierarchy.of(ImmutableBiMap.of(-1, -3), create(1));
	}

	private IndexedHierarchy<Integer, String> createIH(int n) {
		final Hierarchy<Integer> keys = create(n);
		return ImmutableIndexedHierarchy.of(createStringBiMap(keys), keys);
	}

	/**
	 * Consistency.
	 */
	@Test
	public void consistent() {
		for (int i = 0; i < 100; i++) {
			IndexedHierarchy<Integer, String> h = createIH(i);
			self(h);
			self(h.asMap());
			self(h.inverse());
			equality(h.elementSet(), h.asMap().values());
			equality(h.elementSet(), h.asMap().inverse().keySet());
			equality(h.elementSet(), h.inverse().asMap().keySet());
		}
	}

	private static void imEquality(IndexedHierarchy<?, ?> ih1, IndexedHierarchy<?, ?> ih2) {
		equality(ih1, ih2);
		equality(ih1.asMap(), ih2.asMap());
		equality(ih1.inverse(), ih2.inverse());
		equality(ih1.elementSet(), ih2.elementSet());
		equality(ih1.elementSet(), ih2.asMap().values());
		equality(ih1.elementSet(), ih2.asMap().inverse().keySet());
		equality(ih1.elementSet(), ih2.inverse().asMap().keySet());
	}

	/**
	 * Equality.
	 */
	@Test
	public void equality() {
		for (int i = 0; i < 50; i++) {
			final IndexedHierarchy<Integer, String> h1 = createIH(i);
			final IndexedHierarchy<Integer, String> h2 = createIH(i);
			final IndexedHierarchy<Integer, String> hf = new ForwardingIndexedHierarchy<Integer, String>() {
				@Override
				protected IndexedHierarchy<Integer, String> delegate() {
					return h1;
				}
			};
			imEquality(h1, h2);
			imEquality(h1, hf);
			imEquality(h2, hf);
		}
	}

	/**
	 * Built by functions.
	 */
	@Test
	public void functions() {
		Set<Integer> keys = ImmutableSet.of(1, 2, 3, 4, 5, 11, 12, 13, 14, 15);
		Function<Integer, Integer> parent = new Function<Integer, Integer>() {
			public Integer apply(Integer from) {
				return from < 10 ? from + 10 : null;
			}
		};
		final IndexedHierarchy<Integer, String> h = ImmutableIndexedHierarchy.of(keys, Functions.<Integer> identity(),
				parent, Functions.toStringFunction());
		self(h);
		assertEquals(10, h.size());
		assertEquals(5, h.getFirstLevel().size());
		final IndexedHierarchy<String, Integer> ih = h.inverse();
		self(ih);
		assertEquals(10, ih.size());
		assertEquals(5, ih.getFirstLevel().size());
		for (int i = 11; i <= 15; i++) {
			String sp = Integer.toString(i);
			String sc = Integer.toString(i - 10);
			assertTrue(h.getFirstLevel().contains(sp));
			assertEquals(1, h.getChildren(sp).size());
			assertTrue(h.getChildren(sp).contains(sc));
			assertTrue(ih.getFirstLevel().contains(i));
			assertEquals(1, ih.getChildren(i).size());
			assertTrue(ih.getChildren(i).contains(i - 10));
		}
	}

}
