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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Locale;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

/**
 * Tests for LocalizedValues
 * @author Andres Rodriguez
 */
public class LocalizedValuesTest extends AbstractLocaleTest {

	private void check(Localized<String> s) {
		assertNotNull(s);
		assertEquals(s.get(), HELLO);
		assertEquals(s.apply(ES), HOLA);
		assertEquals(s.apply(Locale.ENGLISH), HELLO);
	}

	@Test
	public void fromMap() {
		check(LocalizedValues.fromMap(HELLO, ImmutableMap.of(ES, HOLA)));
	}

	@Test
	public void fromStringMap() {
		check(LocalizedValues.fromStringMap(HELLO, ImmutableMap.of("es", HOLA)));
	}
	
	@Test(expectedExceptions = NullPointerException.class)
	@SuppressWarnings(value = "NP_NONNULL_PARAM_VIOLATION", justification = "Intended for test purposes")
	public void checkNullLocaleKey() {
		check(LocalizedValues.fromMap(HELLO, ImmutableMap.of((Locale)null, HOLA)));
	}

	@Test(expectedExceptions = NullPointerException.class)
	@SuppressWarnings(value = "NP_NONNULL_PARAM_VIOLATION", justification = "Intended for test purposes")
	public void checkNullStringKey() {
		check(LocalizedValues.fromStringMap(HELLO, ImmutableMap.of((String)null, HOLA)));
	}

	@Test(expectedExceptions = NullPointerException.class)
	@SuppressWarnings(value = "NP_NONNULL_PARAM_VIOLATION", justification = "Intended for test purposes")
	public void checkNullLocaleValue() {
		check(LocalizedValues.fromMap(HELLO, ImmutableMap.of(ES, (String)null)));
	}

	@Test(expectedExceptions = NullPointerException.class)
	@SuppressWarnings(value = "NP_NONNULL_PARAM_VIOLATION", justification = "Intended for test purposes")
	public void checkNullStringValue() {
		check(LocalizedValues.fromStringMap(HELLO, ImmutableMap.of(ES.toString(), (String)null)));
	}

	@L7dString(value = HELLO, values = @L7d(locale = "es", value = HOLA))
	static class Test1 {
	}

	@Test
	public void annotation() {
		final L7dString a = Test1.class.getAnnotation(L7dString.class);
		check(LocalizedValues.parse(a));
		check(LocalizedValues.parse(a, "value", "values"));
	}

}
