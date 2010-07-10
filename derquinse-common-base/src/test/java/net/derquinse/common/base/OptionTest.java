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
package net.derquinse.common.base;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.google.inject.internal.Objects;

/**
 * Tests for Option
 * @author Andres Rodriguez
 */
public class OptionTest {
	private static final List<Integer> LIST = Lists.newArrayList(null, 1, 2);

	private static void none(Option<?> n) {
		assertEquals(n.hashCode(), 0);
		assertTrue(n.isEmpty());
		assertFalse(n.isDefined());
		assertTrue(n == Option.none());
	}

	private static <T> Option<T> none(Option<T> n, T fallback) {
		none(n);
		assertTrue(fallback == n.getOrElse(fallback));
		return n;
	}

	/** Validations for the None value. */
	@Test(expectedExceptions = IllegalStateException.class)
	public void none() {
		Option<Integer> n = none(Option.<Integer> none(), 2);
		none(Option.none(), new Object());
		none(Lists.transform(LIST, Option.<Integer> of()).get(0));
		n.get();
	}

	/** Illegal some. */
	@Test(expectedExceptions = NullPointerException.class)
	public void notSome1() {
		Option.some(null);
	}

	/** Illegal some (function version). */
	@Test(expectedExceptions = NullPointerException.class)
	public void notSome2() {
		Lists.transform(LIST, Option.some()).get(0);
	}

	private static <T> Option<T> some(Option<T> s, T value, T other) {
		assertNotNull(s);
		assertNotNull(value);
		assertFalse(value.equals(other));
		assertEquals(s.hashCode(), value.hashCode());
		assertTrue(s.isDefined());
		assertFalse(s.isEmpty());
		assertEquals(Option.some(value), s);
		assertFalse(Objects.equal(other, s.getOrElse(other)));
		return s;
	}

	/** Validations for some value. */
	@Test
	public void some() {
		final Integer one = new Integer(1);
		some(Option.some(one), one, 2);
		some(Option.some(one), 1, 3);
		some(Lists.transform(LIST, Option.of()).get(1), 1, 4);
		some(Lists.transform(LIST.subList(1, 2), Option.some()).get(0), 1, 5);
	}

}
