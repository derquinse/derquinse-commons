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
 * Tests for StringMetaProperty
 * @author Andres Rodriguez
 */
public class IntegerMetaPropertyTest {
	/**
	 * Sample property.
	 */
	@Test
	public void sample() {
		final TestObject obj = new TestObject(0);
		assertEquals(obj.getSample(), Integer.valueOf(0));
		assertEquals(TestObject.SAMPLE.apply(obj), Integer.valueOf(0));
		assertFalse(TestObject.SAMPLE.isValid(null));
		assertTrue(TestObject.SAMPLE.notNull().apply(obj));
		assertFalse(TestObject.SAMPLE.isNull().apply(obj));
		assertTrue(TestObject.SAMPLE.greaterThan(-3).apply(obj));
		assertFalse(TestObject.SAMPLE.lessThan(-3).apply(obj));
	}

	private static final class TestObject {
		/** Descriptor for sample property. */
		public static final IntegerMetaProperty<TestObject> SAMPLE = new IntegerMetaProperty<TestObject>("sample", true) {
			public Integer apply(TestObject input) {
				return input.getSample();
			}
		};

		/** Sample property. */
		private final Integer sample;

		public TestObject(Integer sample) {
			this.sample = SAMPLE.checkValue(sample);
		}

		public Integer getSample() {
			return sample;
		}

		@Override
		public String toString() {
			return Metas.toStringHelper(this).add(SAMPLE).toString();
		}
	}
}
