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
package net.derquinse.common.orm.hib;

import static java.util.UUID.randomUUID;
import static net.derquinse.common.orm.hib.HibLengths.UUID_CHAR;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * Tests for HibLengths
 * @author Andres Rodriguez
 */
public class HibLengthsTest {
	/**
	 * UUID char.
	 */
	@Test
	public void uuidChar() {
		assertEquals(randomUUID().toString().length(), UUID_CHAR);
	}

}
