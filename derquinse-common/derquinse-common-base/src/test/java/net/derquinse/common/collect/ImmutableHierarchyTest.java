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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import net.derquinse.common.collect.ImmutableHierarchy.Builder;
import net.derquinse.common.test.EqualityTests;

import org.testng.annotations.Test;

/**
 * Tests for ImmutableHierarchy.
 * @author Andres Rodriguez
 */
public class ImmutableHierarchyTest extends AbstractHierarchyTest {
	private static Builder<Integer> builder() {
		return ImmutableHierarchy.builder();
	}

	private static Builder<Integer> builder(boolean outOfOrder) {
		return ImmutableHierarchy.builder(outOfOrder);
	}

	/**
	 * Empty hierarchy.
	 */
	@Test
	public void empty() {
		empty(ImmutableHierarchy.of());
		empty(builder().build());
	}

	/**
	 * Null contains.
	 */
	@Test
	public void nullContains() {
		assertFalse(builder().build().elementSet().contains(null));
	}

	/**
	 * Not found.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void notFound1() {
		builder().build().getChildren(1);
	}

	/**
	 * Not found.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void notFound2() {
		builder().build().getParent(1);
	}

	/**
	 * One element.
	 */
	@Test
	public void one() {
		Hierarchy<Integer> h = builder().add(null, 1).build();
		self(h);
		check(h, 1, 1, 2);
		check(h.getFirstLevel(), 1, 1, 2);
		empty(h.getChildren(1));
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
		final Hierarchy<Integer> h = builder.build();
		self(h);
		check(h, 2, 1, 3);
		check(h, 2, 2, 3);
		check(h.getFirstLevel(), 1, 1, 2);
		check(h.getChildren(1), 1, 2, 1);
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
		builder(true).add(1, 2).build();
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

	/**
	 * Equality.
	 */
	@Test
	public void equality() {
		final Hierarchy<Integer> h1 = create(5);
		System.out.println(h1);
		final Hierarchy<Integer> h2 = create(5);
		final Hierarchy<Integer> hf = new ForwardingHierarchy<Integer>() {
			@Override
			protected Hierarchy<Integer> delegate() {
				return h1;
			}
		};
		EqualityTests.many(h1, h2, hf);
	}

}
