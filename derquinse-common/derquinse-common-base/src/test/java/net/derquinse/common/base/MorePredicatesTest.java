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
package net.derquinse.common.base;

import static net.derquinse.common.base.MorePredicates.greaterOrEqual;
import static net.derquinse.common.base.MorePredicates.greaterThan;
import static net.derquinse.common.base.MorePredicates.lessOrEqual;
import static net.derquinse.common.base.MorePredicates.lessThan;
import static net.derquinse.common.test.EqualityTests.two;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.util.Set;

import org.testng.annotations.Test;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * Tests for MorePredicates
 * @author Andres Rodriguez
 */
public class MorePredicatesTest {
	private static final Set<Integer> SET1 = ImmutableSet.of(1, 2, 3);
	private static final Set<Integer> SET2 = ImmutableSet.of(4, 5, 6);

	private <T> void check(Predicate<T> p, Set<T> set, int count) {
		assertEquals(Sets.filter(set, p).size(), count);
	}

	/**
	 * Comparisons.
	 */
	@Test
	public void comparisons() {
		check(greaterThan(1), SET1, 2);
		check(greaterThan(0), SET1, 3);
		check(greaterOrEqual(1), SET1, 3);
		check(lessThan(8), SET1, 3);
		check(lessOrEqual(8), SET2, 3);
		check(lessOrEqual(5), SET2, 2);
	}

	/**
	 * Test equality.
	 */
	@Test
	public void equality() {
		two(greaterThan(1), greaterThan(1));
		two(greaterOrEqual(2), greaterOrEqual(2));
		two(lessThan(3), lessThan(3));
		two(lessOrEqual(4), lessOrEqual(4));
	}

	/**
	 * Test non-equality.
	 */
	@Test
	public void distinct() {
		assertNotEquals(greaterThan(1), greaterOrEqual(1));
		assertNotEquals(greaterThan(1), lessThan(1));
		assertNotEquals(greaterThan(1), lessOrEqual(1));
		assertNotEquals(greaterOrEqual(1), lessThan(1));
		assertNotEquals(lessThan(1), lessOrEqual(1));
	}

}
