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
package net.derquinse.common.i18n;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Locale;

import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

/**
 * Tests for LocalizedStrings
 * @author Andres Rodriguez
 */
public class LocalizedStringsTest extends AbstractLocaleTest {

	private void check(Localized<String> s) {
		assertNotNull(s);
		assertEquals(s.get(), HELLO);
		assertEquals(s.get(ES), HOLA);
		assertEquals(s.get(Locale.ENGLISH), HELLO);
	}

	@Test
	public void fromLocaleMap() {
		check(LocalizedStrings.fromLocaleMap(HELLO, ImmutableMap.of(ES, HOLA)));
	}

	@Test
	public void fromStringMap() {
		check(LocalizedStrings.fromStringMap(HELLO, ImmutableMap.of("es", HOLA)));
	}

	@L7dString(value = HELLO, values = @L7d(locale = "es", value = HOLA))
	static class Test1 {
	}

	@Test
	public void annotation() {
		final L7dString a = Test1.class.getAnnotation(L7dString.class);
		check(LocalizedStrings.parse(a));
		check(LocalizedStrings.parse(a, "value", "values"));
	}

}
