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

import org.testng.annotations.Test;

/**
 * Tests for long basic populations.
 * @author Andres Rodriguez
 */
public class LongPopulationTest {
	/**
	 * Various tests.
	 */
	@Test
	public void tests() {
		LongPopulation p = Populations.ofLong();
		final long t0 = System.nanoTime();
		for (int i = -5000000; i <= 5000000; i++) {
			p = p.add(i);
		}
		final long duration = System.nanoTime() - t0;
		final long ms = duration / 1000000;
		final long pu = duration / p.getCount();
		System.out.println(p);
		System.out.println(String.format("Total time %d ms (%d ns pu)", ms, pu));
	}
}
