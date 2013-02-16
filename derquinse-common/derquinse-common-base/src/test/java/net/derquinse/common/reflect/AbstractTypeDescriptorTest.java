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

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

/**
 * Tests for AbstractTypeDescriptor
 * @author Andres Rodriguez
 */
public class AbstractTypeDescriptorTest {
	/**
	 * Non-generic.
	 */
	@Test
	public void string() {
		TypeDescriptor<String> d = new AbstractTypeDescriptor<String>() {};
		assertTrue(d.getType().equals(String.class));
		assertTrue(d.getRawType().equals(String.class));
	}

	/**
	 * Generic.
	 */
	@Test
	public void generic() {
		TypeDescriptor<List<String>> d = new AbstractTypeDescriptor<List<String>>() {};
		assertTrue(d.getType().equals(Types.listOf(String.class).getType()));
		assertTrue(d.getRawType().equals(List.class));
	}

}
