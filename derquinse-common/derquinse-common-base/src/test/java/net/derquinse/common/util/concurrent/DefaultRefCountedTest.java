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
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import net.derquinse.common.base.Disposable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

/**
 * Tests for AtomicIntegerWaterMark
 * @author Andres Rodriguez
 */
public class DefaultRefCountedTest {
	private Target t;
	private RefCounted<Target> ref;

	@BeforeMethod
	public void before() {
		t = new Target();
		ref = Refs.counted(t, t);
	}

	@AfterMethod
	public void after() throws Exception {
		ref.shutdown().get(30, TimeUnit.SECONDS);
		assertEquals(t.getClosed(), 1);
	}

	private void count(int current) {
		assertEquals(ref.getCount(), current);
	}

	private void count(int current, int max) {
		count(current);
		assertEquals(ref.getMaxCount(), max);
	}

	/**
	 * Simple.
	 */
	@Test
	public void simple() {
		count(0, 0);
		Disposable<Target> d1 = ref.get();
		count(1, 1);
		d1.dispose();
		count(0, 1);
	}

	/**
	 * Less Simple.
	 */
	@Test(dependsOnMethods = "simple")
	public void lessSimple() {
		count(0, 0);
		final int n = 100;
		List<Disposable<Target>> d = Lists.newArrayListWithCapacity(n);
		for (int i = 0; i < n; i++) {
			d.add(ref.get());
			count(i + 1, i + 1);
		}
		for (int i = 0; i < n; i++) {
			d.remove(0).dispose();
			count(n - i - 1, n);
		}
		for (int i = 0; i < n; i++) {
			d.add(ref.get());
			count(i + 1, n);
		}
		ref.shutdown();
		for (int i = 0; i < n; i++) {
			d.remove(0).dispose();
			count(n - i - 1, n);
		}
	}

	/**
	 * Get after dispose.
	 */
	@Test(dependsOnMethods = "lessSimple", expectedExceptions = IllegalStateException.class)
	public void afterDispose() {
		final Disposable<Target> d = ref.get();
		assertNotNull(d.get());
		assertNotNull(d.get());
		d.dispose();
		d.get();
	}

	/**
	 * Get after shutdown.
	 */
	@Test(dependsOnMethods = "lessSimple", expectedExceptions = IllegalStateException.class)
	public void afterShutdown() {
		final Disposable<Target> d = ref.get();
		d.dispose();
		ref.shutdown();
		ref.get();
	}

	/**
	 * Concurrent.
	 */
	@Test(dependsOnMethods = { "afterDispose", "afterShutdown" })
	public void concurrent() throws Exception {
		final SecureRandom rnd = new SecureRandom();
		final AtomicInteger c = new AtomicInteger();
		final Runnable r = new Runnable() {
			@Override
			public void run() {
				final Disposable<Target> d = ref.get();
				c.getAndAdd(1);
				try {
					Thread.sleep(rnd.nextInt(20));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				d.dispose();
			}
		};
		final ExecutorService e = Executors.newFixedThreadPool(20);
		final int n = 1000;
		for (int i = 0; i < n; i++) {
			e.execute(r);
		}
		e.shutdown();
		assertTrue(e.awaitTermination(60, TimeUnit.SECONDS));
		assertTrue(n == c.get());
	}

	private static final class Target implements Runnable {
		private volatile int closed = 0;

		public int getClosed() {
			return closed;
		}

		@Override
		public void run() {
			closed++;
		}
	}

}
