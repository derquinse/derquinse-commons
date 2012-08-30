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
package net.derquinse.common.test.meta;

import java.util.UUID;

import net.derquinse.common.test.EqualityTests;

import org.testng.annotations.Test;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

/**
 * Tests for EqualityTests
 * @author Andres Rodriguez
 */
public class EqualityTestsTest {
	/**
	 * One.
	 */
	@Test
	public void one() {
		EqualityTests.one("String");
	}

	/**
	 * Bad one.
	 */
	@Test(expectedExceptions = Throwable.class)
	public void badOne() {
		EqualityTests.one(new Object() {
			@Override
			@SuppressWarnings(value = "EQ_ALWAYS_TRUE", justification = "Intended for test purposes")
			public boolean equals(Object obj) {
				return true;
			}
		});
	}

	/**
	 * Two.
	 */
	@Test
	public void two() {
		EqualityTests.two(1 + 1, 3 - 1);
	}

	/**
	 * Bad two.
	 */
	@Test(expectedExceptions = Throwable.class)
	public void badTwo() {
		EqualityTests.two(UUID.randomUUID(), UUID.randomUUID());
	}

	/**
	 * Many.
	 */
	@Test
	public void many() {
		String s = UUID.randomUUID().toString();
		EqualityTests.many(s, new String(s), new String(s), new String(s));
	}

	/**
	 * Bad many.
	 */
	@Test(expectedExceptions = Throwable.class)
	public void badMany() {
		String s = UUID.randomUUID().toString();
		EqualityTests.many(new String(s), new String(s), new String(s), 3);
	}

}
