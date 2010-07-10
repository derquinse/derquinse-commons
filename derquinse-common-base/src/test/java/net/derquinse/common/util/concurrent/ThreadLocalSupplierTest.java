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
package net.derquinse.common.util.concurrent;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.Test;

import com.google.common.base.Supplier;

/**
 * Tests for ThreadLocalSupplier.
 * @author Andres Rodriguez
 */
public class ThreadLocalSupplierTest {
	/** Internal supplier. */
	private static final Supplier<Integer> SUPPLIER = new Supplier<Integer>() {
		private final AtomicInteger n = new AtomicInteger();

		public Integer get() {
			return n.incrementAndGet();
		}
	};

	public ThreadLocalSupplierTest() {
	}

	@Test
	public void test() throws Exception {
		final ThreadLocalSupplier<Integer> tls = ThreadLocalSupplier.of(SUPPLIER);
		final Callable<Integer> c = new Callable<Integer>() {
			public Integer call() throws Exception {
				Integer i1 = tls.get();
				Integer i2 = tls.get();
				assertNotNull(i1);
				assertNotNull(i2);
				assertEquals(i1, i2);
				return i1;
			}
		};
		Future<Integer> f1 = Executors.newSingleThreadExecutor().submit(c);
		Future<Integer> f2 = Executors.newSingleThreadExecutor().submit(c);
		Integer i1 = f1.get();
		Integer i2 = f2.get();
		assertNotNull(i1);
		assertNotNull(i2);
		assertFalse(i1.equals(i2));
	}

}
