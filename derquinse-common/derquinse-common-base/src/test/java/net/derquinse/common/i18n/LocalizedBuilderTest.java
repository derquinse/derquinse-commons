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

import java.util.Locale;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Tests for LocalizedBuilder
 * @author Andres Rodriguez
 */
public class LocalizedBuilderTest extends AbstractLocaleTest {
	private LocalizedBuilder<String> builder;

	@BeforeMethod
	public void init() {
		builder = LocalizedBuilder.create();
	}

	@Test
	public void builder() {
		builder.setDefault(HELLO);
		builder.put(ES, HOLA);
		final Localized<String> s = builder.build();
		assertEquals(s.get(), HELLO);
		assertEquals(s.apply(ES), HOLA);
		assertEquals(s.apply(Locale.CANADA), HELLO);
	}

	@Test(expectedExceptions = NullPointerException.class)
	@SuppressFBWarnings(value = "NP_NULL_PARAM_DEREF_ALL_TARGETS_DANGEROUS", justification = "Intended for test purposes")
	public void checkNullKey() {
		builder.put((Locale) null, "hello");
	}

	@Test(expectedExceptions = NullPointerException.class)
	@SuppressFBWarnings(value = "NP_NULL_PARAM_DEREF_ALL_TARGETS_DANGEROUS", justification = "Intended for test purposes")
	public void checkNullValue() {
		builder.put(Locale.CANADA, null);
	}

	@Test(expectedExceptions = IllegalStateException.class)
	public void noDefault() {
		builder.build();
	}

	@Test(expectedExceptions = NullPointerException.class)
	@SuppressFBWarnings(value = "NP_NULL_PARAM_DEREF_NONVIRTUAL", justification = "Intended for test purposes")
	public void nullDefault1() {
		LocalizedBuilder.create(null);
	}

	@Test(expectedExceptions = NullPointerException.class)
	@SuppressFBWarnings(value = "NP_NULL_PARAM_DEREF_ALL_TARGETS_DANGEROUS", justification = "Intended for test purposes")
	public void nullDefault2() {
		LocalizedBuilder.create().setDefault(null);
	}

	@Test(expectedExceptions = NullPointerException.class)
	@SuppressFBWarnings(value = "NP_NULL_PARAM_DEREF_ALL_TARGETS_DANGEROUS", justification = "Intended for test purposes")
	public void nullDefault3() {
		LocalizedBuilder.create().setDefault(new Object()).setDefault(null);
	}

}
