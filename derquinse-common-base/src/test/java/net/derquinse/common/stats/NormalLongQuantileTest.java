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
package net.derquinse.common.stats;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import net.derquinse.common.math.PartialRealFunction;

import org.testng.annotations.Test;

/**
 * Tests for long basic populations.
 * @author Andres Rodriguez
 */
public class NormalLongQuantileTest {
	/**
	 * Various tests.
	 */
	@Test
	public void tests() {
		LongPopulation p = Populations.ofLong();
		for (int i = 0; i < 100; i++) {
			p = p.add(i);
		}
		PartialRealFunction q = Populations.normalQuantile(p);
		assertEquals(q.apply(1), (double) p.getMax());
		assertEquals(q.apply(0.5), p.getMean());
		assertTrue(q.isDefinedAt(0));
		assertTrue(q.isDefinedAt(1));
		assertFalse(q.isDefinedAt(-0.0001));
		assertFalse(q.isDefinedAt(1.0001));
		System.out.println(p);
		System.out.println(q);
	}
}
