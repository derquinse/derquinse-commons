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
package net.derquinse.common.reflect;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import org.testng.annotations.Test;

/**
 * Tests for Types.
 * @author Andres Rodriguez
 */
public class ProxiesTest {
	/** Test interface. */
	private interface Type {
		Integer method();
	}

	/**
	 * Test alwaysNull.
	 */
	@Test
	public void testNull() {
		Type t = Proxies.alwaysNull(Type.class);
		assertNotNull(t);
		assertNull(t.method());
	}

	/**
	 * Test unsupported.
	 */
	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void testUnsupported() {
		Type t = Proxies.unsupported(Type.class);
		assertNotNull(t);
		t.method();
	}
}
