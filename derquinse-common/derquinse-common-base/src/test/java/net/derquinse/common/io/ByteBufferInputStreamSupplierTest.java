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
package net.derquinse.common.io;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.testng.annotations.Test;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.io.ByteStreams;

/**
 * Tests for ByteBufferInputStreamSupplier
 * @author Andres Rodriguez
 */
public class ByteBufferInputStreamSupplierTest {
	private static final SecureRandom random = new SecureRandom();
	private static final ExecutorService executor = Executors.newFixedThreadPool(5);

	/**
	 * Context test.
	 */
	@Test
	public void test() throws Exception {
		for (int i = 128; i <= 1024; i++) {
			test(i);
		}
	}

	private void test(int length) throws Exception {
		final byte[] data = new byte[length];
		random.nextBytes(data);
		final ByteBuffer buffer = ByteBuffer.wrap(data);
		final ByteBufferInputStreamSupplier supplier = new ByteBufferInputStreamSupplier(buffer);
		final Callable<byte[]> callable = new Callable<byte[]>() {
			@Override
			public byte[] call() throws Exception {
				return ByteStreams.toByteArray(supplier);
			}
		};
		final Multiset<Callable<byte[]>> set = HashMultiset.create();
		set.add(callable, 100);
		for (Future<byte[]> f : executor.invokeAll(set)) {
			assertEquals(f.get(), data);
		}
		final InputStream is1 = new ByteArrayInputStream(data);
		final InputStream is2 = supplier.getInput();
		for (int i = 0; i < length; i++) {
			int i1 = is1.read();
			int i2 = is2.read();
			assertTrue(i1 != -1 && i1 == i2);
		}
		assertEquals(is1.read(), -1);
		assertEquals(is2.read(), -1);
		assertEquals(supplier.getInput().skip(length + 100), length);
	}
}
