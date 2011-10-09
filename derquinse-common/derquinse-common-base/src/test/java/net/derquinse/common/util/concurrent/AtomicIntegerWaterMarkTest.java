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

import net.derquinse.common.base.IntegerWaterMark;
import net.derquinse.common.util.concurrent.AtomicIntegerWaterMark;

import org.testng.annotations.Test;

/**
 * Tests for AtomicIntegerWaterMark
 * @author Andres Rodriguez
 */
public class AtomicIntegerWaterMarkTest {
	private AtomicIntegerWaterMark m;
	private SecureRandom r = new SecureRandom();

	private void test(IntegerWaterMark value, int current) {
		assertNotNull(value);
		assertEquals(value.get(), current);
		assertTrue(value.getMin() <= current);
		assertTrue(value.getMax() >= current);
	}

	private void test(int current) {
		assertNotNull(m);
		test(m.get(), current);
	}

	/**
	 * Empty.
	 */
	@Test
	public void empty() {
		m = AtomicIntegerWaterMark.of();
		test(0);
	}

	/**
	 * Initial value.
	 */
	@Test(dependsOnMethods = "empty")
	public void initial() {
		m = AtomicIntegerWaterMark.of(7);
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
	
	private synchronized int rnd() {
		return r.nextInt();
	}

	/**
	 * Mutations.
	 */
	@Test(dependsOnMethods = "mutation", invocationCount = 1000, threadPoolSize = 20)
	public void threads() {
		final int v = rnd();
		test(m.set(v), v);
	}

}
