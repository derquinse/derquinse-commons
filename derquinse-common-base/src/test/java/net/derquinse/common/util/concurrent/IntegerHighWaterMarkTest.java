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

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for IntegerHighWaterMark.
 * @author Andres Rodriguez
 */
public class IntegerHighWaterMarkTest {
	/** Test target. */
	private IntegerHighWaterMark hwm;

	public IntegerHighWaterMarkTest() {
	}

	private void check(int value) {
		Assert.assertEquals(hwm.intValue(), value);
	}

	private void set(int value) {
		hwm.set(value);
		check(value);
	}

	private void set(int value, int expected) {
		hwm.set(value);
		check(expected);
	}

	@Test
	public void test() {
		hwm = IntegerHighWaterMark.create();
		check(0);
		set(20);
		set(1, 20);
		set(30);
		set(20, 30);
	}

}
