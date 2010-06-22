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

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for long basic populations.
 * @author Andres Rodriguez
 */
public class TimingMapTest {
	private static final int NK = 100;
	private static final int NT = 5000;

	/**
	 * Map tests.
	 */
	@Test
	public void map() {
		final long t0 = System.nanoTime();
		TimingMap<Integer> map = Timings.createMap(TimeUnit.MILLISECONDS);
		for (int i = 0; i < NK; i++) {
			for (long j = 0; j < NT; j++) {
				map.add(Integer.valueOf(i), j);
			}
		}
		final long t1 = System.nanoTime();
		for (int i = 0; i < NK; i++) {
			for (int j = 0; j < NK; j++) {
				Assert.assertEquals(map.get(i), map.get(j));
			}
		}
		final long t2 = System.nanoTime();
		final long d1 = t1 - t0;
		final long d2 = t2 - t1;
		final long ms1 = d1 / 1000000;
		final long pu1 = d1 / (NK * NT);
		final long ms2 = d2 / 1000000;
		final long pu2 = d2 / (NK * NK);
		System.out.println(String.format("Creation time %d ms (%d ns pu)", ms1, pu1));
		System.out.println(String.format("Check time %d ms (%d ns pu)", ms2, pu2));
	}

	/**
	 * Accumulating tests.
	 */
	@Test(dependsOnMethods = "map")
	public void accumulating() {
		final long t0 = System.nanoTime();
		AccumulatingTimingMap<Integer> map = Timings.createAccumulatingMap(TimeUnit.MILLISECONDS);
		Timing t = Timings.create(TimeUnit.MILLISECONDS);
		for (int i = 0; i < NK; i++) {
			for (long j = 0; j < NT; j++) {
				map.add(Integer.valueOf(i), j);
				t = t.add(j);
			}
		}
		final long t1 = System.nanoTime();
		Assert.assertEquals(map.getAccumulator(), t);
		final long d1 = t1 - t0;
		final long ms1 = d1 / 1000000;
		final long pu1 = d1 / (NK * NT);
		System.out.println(String.format("Creation time %d ms (%d ns pu)", ms1, pu1));
	}

}
