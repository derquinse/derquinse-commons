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

import net.derquinse.common.test.GsonSerializabilityTests;

import org.testng.annotations.Test;

/**
 * Tests for GsonSerializabilityTests
 * @author Andres Rodriguez
 */
public class GsonSerializabilityTestsTest {
	/**
	 * String.
	 */
	@Test
	public void string() {
		GsonSerializabilityTests.check(UUID.randomUUID().toString());
	}

	/**
	 * Integer.
	 */
	@Test
	public void integer() {
		GsonSerializabilityTests.check(1);
	}

	/**
	 * UUID.
	 */
	@Test
	public void uuid() {
		GsonSerializabilityTests.check(UUID.randomUUID());
	}

}
