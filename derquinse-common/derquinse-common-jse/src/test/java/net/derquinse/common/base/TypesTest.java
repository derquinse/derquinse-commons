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
package net.derquinse.common.base;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotSame;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

/**
 * Tests for Types.
 * @author Andres Rodriguez
 */
public class TypesTest {
	/**
	 * Test String.
	 */
	@Test
	public void testString() {
		@SuppressWarnings("serial")
		final Object o = new ArrayList<String>() {
		};
		final Type type = Types.getSuperclassTypeArgument(o.getClass());
		assertEquals(type, String.class);
		assertEquals(Types.getRawType(type), String.class);
	}

	/**
	 * Test String list.
	 */
	@Test
	public void testStringList() {
		@SuppressWarnings("serial")
		final Object o = new ArrayList<List<String>>() {
		};
		final Type type = Types.getSuperclassTypeArgument(o.getClass());
		assertNotSame(type, List.class);
		assertEquals(Types.getRawType(type), List.class);
	}
}
