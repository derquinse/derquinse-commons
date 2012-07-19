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
package net.derquinse.common.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

/**
 * Equality tests support methods.
 * @author Andres Rodriguez
 */
public final class EqualityTests {
	/** Not instantiable. */
	private EqualityTests() {
		throw new AssertionError();
	}

	/**
	 * Checks equality for one object. The object must be not null, equal to itself (the relation is
	 * reflexive), not equal to null.
	 * @param obj Object to test.
	 */
	@SuppressWarnings(value = "EC_NULL_ARG", justification = "Intended for test purposes")
	public static void one(Object obj) {
		assertNotNull(obj, "The provided object is null");
		assertTrue(obj.equals(obj), String.format("The object [%s] is not equal to itself", obj));
		assertFalse(obj.equals(null), String.format("The object [%s] is equal to null", obj));
	}

	private static void checkTwo(Object obj1, Object obj2) {
		assertTrue(obj1.equals(obj2), String.format("The object [%s] is not equal to [%s]", obj1, obj2));
	}

	private static void internalTwo(Object obj1, Object obj2) {
		assertEquals(obj1.hashCode(), obj2.hashCode(),
				String.format("The object [%s]'s hash code is not equal to [%s]'s", obj1, obj2));
		checkTwo(obj1, obj2);
		checkTwo(obj2, obj1);
	}

	/**
	 * Checks equality for two objects. The objects are checked for single object equality (@see
	 * #one(Object)} that their hash codes are the same (the relation is symmetric).
	 * @param obj1 First object to test.
	 * @param obj2 Second object to test.
	 */
	public static void two(Object obj1, Object obj2) {
		one(obj1);
		one(obj2);
		internalTwo(obj1, obj2);
	}

	/**
	 * Checks equality for many object. The objects are tested pairwise (see
	 * {@link #two(Object, Object)}), so that the relation is transitive.
	 * @param objects Objects to test.
	 */
	public static void many(@Nullable List<?> objects) {
		if (objects == null || objects.isEmpty()) {
			return;
		}
		for (int i = 0; i < objects.size(); i++) {
			final Object obj1 = objects.get(i);
			if (i == 0) {
				one(obj1);
			}
			for (int j = i + 1; j < objects.size(); j++) {
				final Object obj2 = objects.get(j);
				if (i == 0) {
					one(obj2);
				}
				internalTwo(obj1, obj2);
			}
		}
	}

	/**
	 * Checks equality for many object. The objects are tested pairwise (see
	 * {@link #two(Object, Object)}), so that the relation is transitive.
	 * @param objects Objects to test.
	 */
	public static void many(Object... objects) {
		if (objects == null || objects.length == 0) {
			return;
		}
		many(Arrays.asList(objects));
	}

}
