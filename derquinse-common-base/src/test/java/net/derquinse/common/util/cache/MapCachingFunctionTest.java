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
package net.derquinse.common.util.cache;

import static net.derquinse.common.util.cache.CachingFunctions.byMap;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;

/**
 * Tests for MapCachingFunction.
 * @author Andres Rodriguez
 */
public class MapCachingFunctionTest {
	private static final String HI = "hi!";
	private static final Function<Object, String> F = new Function<Object, String>() {
		public String apply(Object from) {
			try {
				TimeUnit.MILLISECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return HI;
		}

		@Override
		public String toString() {
			return "test";
		}
	};

	/**
	 * Normal.
	 */
	@Test
	public void normal() {
		CachingFunction<Object, String> f = byMap(new MapMaker(), F);
		assertNotNull(f);
		assertEquals(f.getRequested(), 0);
		assertEquals(f.getComputed(), 0);
		assertEquals(f.apply(-1), HI);
		assertEquals(f.apply(-2), HI);
		assertEquals(f.getRequested(), 2);
		assertEquals(f.getComputed(), 2);
		assertEquals(f.apply(-1), HI);
		assertEquals(f.apply(-2), HI);
		assertEquals(f.getRequested(), 4);
		assertEquals(f.getComputed(), 2);
	}

	private void exercise(CachingFunction<Object, String> f) {
		final int n = 100;
		for (int i = 0; i < n; i++) {
			assertEquals(f.apply(i), HI);
		}
		assertEquals(f.getRequested(), n);
		assertEquals(f.getComputed(), n);
		for (int i = 0; i < n; i++) {
			assertEquals(f.apply(i), HI);
		}
		assertEquals(f.getRequested(), 2 * n);
		long c = f.getComputed();
		assertTrue(c >= n && c <= 2 * n);
		System.out.println(f);
	}

	/**
	 * Different configurations.
	 */
	@Test(dependsOnMethods = "normal")
	public void exercise() {
		exercise(byMap(new MapMaker(), F));
		exercise(byMap(new MapMaker().softValues(), F));
		exercise(byMap(new MapMaker().softKeys(), F));
		exercise(byMap(new MapMaker().softKeys().softValues(), F));
		exercise(byMap(new MapMaker().expiration(250, TimeUnit.MILLISECONDS), F));
		exercise(byMap(new MapMaker().softValues().expiration(250, TimeUnit.MILLISECONDS), F));
		exercise(byMap(new MapMaker().softValues().expiration(10, TimeUnit.SECONDS), F));
	}

}
