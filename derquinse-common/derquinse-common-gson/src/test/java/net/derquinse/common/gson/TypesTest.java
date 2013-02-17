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
package net.derquinse.common.gson;

import java.util.List;

import net.derquinse.common.reflect.Types;
import net.derquinse.common.test.GsonSerializabilityTests;

import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;

/**
 * Tests for using Guava's {@link TypeToken} with Gson.
 * @author Andres Rodriguez
 */
public class TypesTest {
	/** Long list. */
	@Test
	public void longList() {
		List<Long> list = Lists.newArrayList(1L, 3L, 5L);
		GsonSerializabilityTests.check(DerquinseGson.get(), list, Types.listOf(Long.class).getType());
	}
}
