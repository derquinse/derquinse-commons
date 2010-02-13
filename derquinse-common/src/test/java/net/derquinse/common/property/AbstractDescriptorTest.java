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
package net.derquinse.common.property;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Set;

import org.testng.annotations.Test;

/**
 * Test for AbstractDescriptor.
 * @author Andres Rodriguez
 */
public class AbstractDescriptorTest {
	public AbstractDescriptorTest() {
	}

	@Test
	public void raw() {
		assertTrue(new AbstractDescriptor<String>() {
		}.isRawType());
	}

	@Test
	public void notRaw() {
		assertFalse(new AbstractDescriptor<Set<String>>() {
		}.isRawType());
	}

}
