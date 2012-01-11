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
package net.derquinse.common.uuid;

import static java.util.UUID.randomUUID;
import static net.derquinse.common.uuid.UUIDs.safeFromString;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.UUID;

import org.testng.annotations.Test;

/**
 * Tests for UUIDs
 * @author Andres Rodriguez
 */
public class UUIDsTest {

	@Test
	public void testOk() {
		final UUID uuid = randomUUID();
		final String s = uuid.toString();
		assertEquals(UUID.fromString(s), uuid);
		assertEquals(UUIDs.fromString().apply(s), uuid);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void checkNullValue() {
		UUIDs.fromString().apply(null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void bad01() {
		UUIDs.fromString().apply("");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void bad02() {
		UUIDs.fromString().apply("_");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void bad03() {
		UUIDs.fromString().apply("1_2_3_4_5");
	}

	@Test
	public void testSafe() {
		assertNull(safeFromString(null));
		assertNull(safeFromString().apply(null));
		assertNull(safeFromString(""));
		assertNull(safeFromString().apply(""));
		final UUID uuid = randomUUID();
		final String s = uuid.toString();
		assertNotNull(safeFromString(s));
		assertNotNull(safeFromString().apply(s));
		assertEquals(safeFromString(s), uuid);
		assertEquals(safeFromString().apply(s), uuid);
	}

}
