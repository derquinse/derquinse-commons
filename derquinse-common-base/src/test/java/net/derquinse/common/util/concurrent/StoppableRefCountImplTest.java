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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.util.concurrent.MoreExecutors;

/**
 * Tests for StoppableRefCountImpl
 * @author Andres Rodriguez
 */
public class StoppableRefCountImplTest {
	private static final Object VALUE = new Object();
	
	private final ExecutorService es = MoreExecutors.sameThreadExecutor();
	private StoppableRefCount<Object> ref;
	private Counter counter;
	
	@BeforeMethod
	public void before() {
		ref = new StoppableRefCountImpl<Object>(VALUE);
		counter = new Counter();
	}
	
	private Object get() {
		Object o = ref.get();
		assertEquals(o, VALUE);
		return o;
	}

	private void dispose() {
		ref.dispose();
	}
	
	private void check(int times) {
		counter.check(times);
	}
	
	private void stop() {
		ref.stop().addListener(counter, es);
	}
	
	
	@Test
	public void simple() {
		get();
		dispose();
		get();
		get();
		dispose();
		dispose();
		check(0);
		stop();
		check(1);
	}

	@Test(expectedExceptions=IllegalStateException.class)
	public void badDispose() {
		get();
		dispose();
		dispose();
	}

	@Test(dependsOnMethods="simple")
	public void waiter() {
		get();
		get();
		get();
		stop();
		check(0);
		dispose();
		check(0);
		dispose();
		check(0);
		dispose();
		check(1);
	}
}
