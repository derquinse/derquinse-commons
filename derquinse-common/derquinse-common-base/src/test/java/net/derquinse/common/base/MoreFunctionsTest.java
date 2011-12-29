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
package net.derquinse.common.base;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Optional;

/**
 * Tests for MoreFunctions.
 * @author Andres Rodriguez
 */
public class MoreFunctionsTest {
	/**
	 * Null safe.
	 */
	@Test
	public void nullSafe() {
		Function<Object, String> f = MoreFunctions.nullSafe(Functions.toStringFunction());
		assertNull(f.apply(null));
		assertNotNull(f.apply(f));
	}

	/**
	 * From nullable.
	 */
	@Test
	public void fromNullable() {
		Function<Integer, Optional<Integer>> f = MoreFunctions.fromNullable();
		assertTrue(f.apply(3).isPresent());
		assertFalse(f.apply(null).isPresent());
	}

}
