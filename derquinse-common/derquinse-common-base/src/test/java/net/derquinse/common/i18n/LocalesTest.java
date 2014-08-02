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
package net.derquinse.common.i18n;

import static net.derquinse.common.i18n.Locales.fromString;
import static net.derquinse.common.i18n.Locales.safeFromString;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.Locale;

import org.testng.annotations.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Tests for Locales
 * @author Andres Rodriguez
 */
public class LocalesTest {

	private void checkOk(String s, Locale locale) {
		assertEquals(fromString(s), locale);
		assertEquals(fromString().apply(s), locale);
	}

	@Test
	public void testOk() {
		checkOk("en", new Locale("en"));
		checkOk("en_EN", new Locale("en", "EN"));
		checkOk("en_EN_WIN", new Locale("en", "EN", "WIN"));
	}

	@Test(expectedExceptions = NullPointerException.class)
	@SuppressFBWarnings(value = "NP_NULL_PARAM_DEREF_NONVIRTUAL", justification = "Intended for test purposes")
	public void checkNullValue() {
		fromString(null);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void bad01() {
		fromString("");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void bad02() {
		fromString("_");
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void bad03() {
		fromString("1_2_3_4_5");
	}

	@Test
	public void testSafe() {
		assertNull(safeFromString(null));
		assertNull(safeFromString().apply(null));
		assertNull(safeFromString(""));
		assertNull(safeFromString().apply(""));
		assertNotNull(safeFromString("en"));
		assertNotNull(safeFromString().apply("en"));
		assertEquals(safeFromString("en_EN"), new Locale("en", "EN"));
		assertEquals(safeFromString().apply("en_EN"), new Locale("en", "EN"));
	}

}
