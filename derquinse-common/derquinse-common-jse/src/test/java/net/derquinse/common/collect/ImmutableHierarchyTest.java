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

import java.util.List;

import org.testng.annotations.Test;

/**
 * Tests for ImmutableHierarchy.
 * @author Andres Rodriguez
 */
public class ImmutableHierarchyTest {
	private static ImmutableHierarchy.Builder<Integer> builder() {
		return ImmutableHierarchy.builder();
	}

	private static ImmutableHierarchy.Builder<Integer> builder(boolean outOfOrder) {
		return ImmutableHierarchy.builder(outOfOrder);
	}

	private void empty(Hierarchy<Integer> h) {
		assertNotNull(h);
		assertNotNull(h.getFirstLevel());
		assertTrue(h.getFirstLevel().isEmpty());
		assertFalse(h.elements().contains(1));
	}

	/**
	 * Empty hierarchy.
	 */
	@Test
	public void empty() {
		empty(ImmutableHierarchy.<Integer> of());
		empty(builder().get());
	}

	/**
	 * Null contains.
	 */
	@Test
	public void nullContains() {
		assertFalse(builder().get().elements().contains(null));
	}

	/**
	 * Not found.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void notFound1() {
		builder().get().getChildren(1);
	}

	/**
	 * Not found.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void notFound2() {
		builder().get().getParent(1);
	}

	private void check(Hierarchy<Integer> h, Integer yes, Integer no) {
		assertNotNull(h);
		assertTrue(h.elements().contains(yes));
		assertFalse(h.elements().contains(no));
	}

	private void checkList(List<Integer> list, int size, Integer yes, Integer no) {
		assertNotNull(list);
		assertTrue(list.size() == size);
		assertTrue(list.contains(yes));
		assertFalse(list.contains(no));
	}

	/**
	 * One element.
	 */
	@Test
	public void one() {
		Hierarchy<Integer> h = builder().add(null, 1).get();
		check(h, 1, 2);
		checkList(h.getFirstLevel(), 1, 1, 2);
	}

	/**
	 * Duplicate element.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void dup1() {
		builder().add(null, 1).add(null, 1);
	}

	/**
	 * Duplicate element.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void dup2() {
		builder().add(null, 1).add(1, 1);
	}

	/**
	 * Duplicate element.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void dup3() {
		builder().add(null, 1).add(1, 2).add(2, 1);
	}

	private void checkTwo(ImmutableHierarchy.Builder<Integer> builder) {
		final Hierarchy<Integer> h = builder.get();
		check(h, 1, 3);
		check(h, 2, 3);
		checkList(h.getFirstLevel(), 1, 1, 2);
		checkList(h.getChildren(1), 1, 2, 1);
		assertEquals(h.getParent(2), Integer.valueOf(1));
	}

	/**
	 * Two elements in order.
	 */
	@Test
	public void twoIO() {
		checkTwo(builder().add(null, 1).add(1, 2));
	}

	/**
	 * Two elements out of order (bad).
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void twoOOBad() {
		checkTwo(builder().add(1, 2).add(null, 1));
	}

	/**
	 * Two elements out of order.
	 */
	@Test
	public void twoOO() {
		checkTwo(builder(true).add(1, 2).add(null, 1));
	}

	/**
	 * Unadded element.
	 */
	@Test(expectedExceptions = IllegalStateException.class)
	public void unadded() {
		builder(true).add(1, 2).get();
	}

	/**
	 * Loop.
	 */
	@Test(expectedExceptions = IllegalStateException.class)
	public void loop1() {
		builder(true).add(1, 2).add(2, 1);
	}

	/**
	 * Loop.
	 */
	@Test(expectedExceptions = IllegalStateException.class)
	public void loop2() {
		builder(true).add(1, 2).add(2, 3).add(3, 4).add(4, 1);
	}
}
