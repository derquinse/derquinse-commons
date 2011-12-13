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
package net.derquinse.common.test;

import java.util.UUID;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Tests for HessianSerializabilityTests
 * @author Andres Rodriguez
 */
public class HessianSerializabilityTestsTest {
	/**
	 * String.
	 */
	@Test
	public void string() {
		HessianSerializabilityTests.both(UUID.randomUUID().toString());
	}

	/**
	 * Integer.
	 */
	@Test
	public void integer() {
		HessianSerializabilityTests.both(1);
	}

	/**
	 * Array list.
	 */
	@Test
	public void arrayList() {
		HessianSerializabilityTests.both(Lists.newArrayList(1, 2));
	}

	/**
	 * Immutable list.
	 */
	@Test
	public void immutableList() {
		HessianSerializabilityTests.both(ImmutableList.of(1, 2));
	}

	/**
	 * Hash map.
	 */
	@Test
	public void hashMap() {
		HessianSerializabilityTests.both(Maps.newHashMap(ImmutableMap.of(1, 2)));
	}

	/**
	 * Immutable map.
	 */
	@Test
	public void immutableMap() {
		HessianSerializabilityTests.both(ImmutableMap.of(1, 2));
	}

	/**
	 * UUID.
	 */
	// @Test
	public void uuid() {
		HessianSerializabilityTests.both(UUID.randomUUID());
	}

}
