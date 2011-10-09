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
package net.derquinse.common.util.concurrent;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.security.SecureRandom;

import net.derquinse.common.base.LongWaterMark;

import org.testng.annotations.Test;

/**
 * Tests for AtomicLongWaterMark
 * @author Andres Rodriguez
 */
public class AtomicLongWaterMarkTest {
	private AtomicLongWaterMark m;
	private SecureRandom r = new SecureRandom();

	private void test(LongWaterMark value, long current) {
		assertNotNull(value);
		assertEquals(value.get(), current);
		assertTrue(value.getMin() <= current);
		assertTrue(value.getMax() >= current);
	}

	private void test(long current) {
		assertNotNull(m);
		test(m.get(), current);
	}

	/**
	 * Empty.
	 */
	@Test
	public void empty() {
		m = AtomicLongWaterMark.of();
		test(0);
	}

	/**
	 * Initial value.
	 */
	@Test(dependsOnMethods = "empty")
	public void initial() {
		m = AtomicLongWaterMark.of(7);
		test(7);
	}

	/**
	 * Mutations.
	 */
	@Test(dependsOnMethods = "initial")
	public void mutation() {
		m.inc();
		test(8);
		m.dec();
		test(7);
		m.dec();
		test(6);
		m.inc();
		test(7);
		m.add(4);
		test(11);
		m.add(-7);
		test(4);
		m.set(9);
		test(9);
	}

	private synchronized long rnd() {
		return r.nextLong();
	}

	/**
	 * Mutations.
	 */
	@Test(dependsOnMethods = "mutation", invocationCount = 1000, threadPoolSize = 20)
	public void threads() {
		final long v = rnd();
		test(m.set(v), v);
	}

}
