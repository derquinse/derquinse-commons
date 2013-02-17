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
package net.derquinse.common.meta;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

/**
 * Tests for {@link MetaField}
 * @author Andres Rodriguez
 */
public class MetaFieldTest {
	private static final String VALUE0 = "VaLue0";

	/**
	 * Name property.
	 */
	@Test
	public void name() {
		final MetaField<Object> meta = new MetaField<Object>(VALUE0) {};
		assertEquals(meta.getName(), VALUE0);
		assertEquals(MetaField.NAME.apply(meta), VALUE0);
		assertFalse(MetaField.NAME.isValid(null));
		assertTrue(MetaField.NAME.notNull().apply(meta));
		assertFalse(MetaField.NAME.isNull().apply(meta));
	}
}
