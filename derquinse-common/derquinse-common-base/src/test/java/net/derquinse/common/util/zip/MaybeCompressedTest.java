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
package net.derquinse.common.util.zip;

import net.derquinse.common.test.GsonSerializabilityTests;
import net.derquinse.common.test.HessianSerializabilityTests;
import net.derquinse.common.test.SerializabilityTests;

import org.testng.annotations.Test;

/**
 * Tests for {@link MaybeCompressed}
 * @author Andres Rodriguez
 */
public class MaybeCompressedTest {

	@Test
	public void serializability() {
		MaybeCompressed<String> m = MaybeCompressed.of(false, "hello");
		SerializabilityTests.check(m);
		HessianSerializabilityTests.both(m);
		GsonSerializabilityTests.check(m);
	}
}
