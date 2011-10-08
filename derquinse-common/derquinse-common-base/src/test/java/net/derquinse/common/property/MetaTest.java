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
package net.derquinse.common.property;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

/**
 * Tests for Meta
 * @author Andres Rodriguez
 */
public class MetaTest {
	private static final String VALUE0 = "VaLue0";

	/**
	 * Name property.
	 */
	@Test
	public void name() {
		final Meta<Object> meta = new Meta<Object>(VALUE0) {};
		assertEquals(meta.getName(), VALUE0);
		assertEquals(Meta.NAME.apply(meta), VALUE0);
		assertFalse(Meta.NAME.isValid(null));
		assertTrue(Meta.NAME.notNull().apply(meta));
		assertFalse(Meta.NAME.isNull().apply(meta));
	}
}
