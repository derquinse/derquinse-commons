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

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.google.common.base.Predicates;

/**
 * Tests for MetaFlag
 * @author Andres Rodriguez
 */
public class MetaFlagTest {
	/**
	 * Sample property.
	 */
	@Test
	public void name() {
		final TestObject t = new TestObject(true);
		final TestObject f = new TestObject(false);
		assertTrue(t.isSample());
		assertTrue(TestObject.SAMPLE.apply(t));
		assertFalse(f.isSample());
		assertFalse(TestObject.SAMPLE.apply(f));
		assertTrue(TestObject.SAMPLE.or(Predicates.alwaysFalse()).apply(t));
		assertFalse(TestObject.SAMPLE.and(Predicates.alwaysFalse()).apply(t));
	}

	private static final class TestObject {
		/** Descriptor for sample flag. */
		public static final MetaFlag<TestObject> SAMPLE = new MetaFlag<TestObject>("sample") {
			public boolean apply(TestObject input) {
				return input.isSample();
			}
		};

		/** Sample property. */
		private final boolean sample;

		public TestObject(boolean sample) {
			this.sample = sample;
		}

		public boolean isSample() {
			return sample;
		}
	}
}
