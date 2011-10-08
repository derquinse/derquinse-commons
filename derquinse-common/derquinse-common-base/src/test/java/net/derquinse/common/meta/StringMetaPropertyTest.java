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

import net.derquinse.common.meta.StringMetaProperty;

import org.testng.annotations.Test;

import com.google.common.base.Function;

/**
 * Tests for StringMetaProperty
 * @author Andres Rodriguez
 */
public class StringMetaPropertyTest {
	private static final String VALUE0 = "VaLue0";

	/**
	 * Sample property.
	 */
	@Test
	public void name() {
		final TestObject obj = new TestObject(VALUE0);
		assertEquals(obj.getSample(), VALUE0);
		assertEquals(TestObject.SAMPLE.apply(obj), VALUE0);
		assertFalse(TestObject.SAMPLE.isValid(null));
		assertTrue(TestObject.SAMPLE.notNull().apply(obj));
		assertFalse(TestObject.SAMPLE.isNull().apply(obj));
		
		final Function<String, String> lower = new Function<String, String>() {
			public String apply(String input) {
				return input.toLowerCase();
			}
		};
		
		assertEquals(VALUE0.toLowerCase(), TestObject.SAMPLE.compose(lower).apply(obj));
	}
	
	private static final class TestObject {
		/** Descriptor for sample property. */
		public static final StringMetaProperty<TestObject> SAMPLE = new StringMetaProperty<TestObject>("sample", true) {
			public String apply(TestObject input) {
				return input.getSample();
			}
		};
		
		/** Sample property. */
		private final String sample;
		
		public TestObject(String sample) {
			this.sample = SAMPLE.checkValue(sample);
		}
		
		public String getSample() {
			return sample;
		}
	}
}
