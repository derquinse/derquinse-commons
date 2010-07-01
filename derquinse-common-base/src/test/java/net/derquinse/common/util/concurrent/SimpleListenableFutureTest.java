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

import java.util.concurrent.ExecutorService;

import org.testng.annotations.Test;

import com.google.common.util.concurrent.MoreExecutors;

/**
 * Tests for SimpleListenableFuture
 * @author Andres Rodriguez
 */
public class SimpleListenableFutureTest {
	private int times = 0;

	@Test
	public void once() {
		final ExecutorService es = MoreExecutors.sameThreadExecutor();
		final Runnable r = new Runnable() {
			public void run() {
				times++;
			}
		};
		final SimpleListenableFuture<Object> f = new SimpleListenableFuture<Object>();
		f.addListener(r, es);
		assertEquals(times, 0);
		f.set(null);
		assertEquals(times, 1);
		f.set(null);
		assertEquals(times, 1);
		f.addListener(r, es);
		assertEquals(times, 2);
		f.set(null);
		assertEquals(times, 2);
	}
}
