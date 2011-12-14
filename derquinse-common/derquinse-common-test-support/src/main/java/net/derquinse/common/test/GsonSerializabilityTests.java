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

import static net.derquinse.common.test.Support.required;
import static org.testng.Assert.assertNotNull;

import java.lang.reflect.Type;

import com.google.gson.Gson;

/**
 * Gson serializability support methods.
 * @author Andres Rodriguez
 */
public final class GsonSerializabilityTests {
	/** Not instantiable. */
	private GsonSerializabilityTests() {
		throw new AssertionError();
	}

	/**
	 * Checks serializability of one object and (if desired) equality of the provided instance and the
	 * deserialized one.
	 * @param gson Gson instance to use.
	 * @param obj Object to test.
	 * @param type Type to use for deserialization.
	 * @param equality Whether to test for equality.
	 * @return The deserialized instance.
	 * @throws UnableToSerializeException
	 * @see {@link EqualityTests#two(Object, Object)}
	 */
	public static <T> T check(Gson gson, T obj, Type type, boolean equality) throws UnableToSerializeException {
		required(obj);
		assertNotNull(type, "The provided Gson instance is null");
		assertNotNull(type, "The provided type is null");
		final Object deserialized;
		try {
			String json = gson.toJson(obj);
			deserialized = gson.fromJson(json, type);
		} catch (Throwable t) {
			throw new UnableToSerializeException(t);
		}
		return SerializabilitySupport.cast(obj, deserialized, equality);
	}

	/**
	 * Checks serializability of one object and equality of the provided instance and the deserialized
	 * one.
	 * @param gson Gson instance to use.
	 * @param obj Object to test.
	 * @param type Type to use for deserialization.
	 * @return The deserialized instance.
	 * @throws UnableToSerializeException
	 * @see {@link EqualityTests#two(Object, Object)}
	 */
	public static <T> T check(Gson gson, T obj, Type type) throws UnableToSerializeException {
		return check(gson, obj, type, true);
	}

	/**
	 * Checks serializability of one object and equality of the provided instance and the deserialized
	 * one.
	 * @param gson Gson instance to use.
	 * @param obj Object to test.
	 * @param type Type to use for deserialization.
	 * @return The deserialized instance.
	 * @throws UnableToSerializeException
	 * @see {@link EqualityTests#two(Object, Object)}
	 */
	public static <T> T check(Gson gson, T obj) throws UnableToSerializeException {
		return check(gson, required(obj), obj.getClass());
	}

	/**
	 * Checks serializability of one object and (if desired) equality of the provided instance and the
	 * deserialized one.
	 * @param obj Object to test.
	 * @param type Type to use for deserialization.
	 * @param equality Whether to test for equality.
	 * @return The deserialized instance.
	 * @throws UnableToSerializeException
	 * @see {@link EqualityTests#two(Object, Object)}
	 */
	public static <T> T check(T obj, Type type, boolean equality) throws UnableToSerializeException {
		return check(new Gson(), obj, type, equality);
	}

	/**
	 * Checks serializability of one object and equality of the provided instance and the deserialized
	 * one.
	 * @param obj Object to test.
	 * @param type Type to use for deserialization.
	 * @return The deserialized instance.
	 * @throws UnableToSerializeException
	 * @see {@link EqualityTests#two(Object, Object)}
	 */
	public static <T> T check(T obj, Type type) throws UnableToSerializeException {
		return check(obj, type, true);
	}

	/**
	 * Checks serializability of one object and (if desired) equality of the provided instance and the
	 * deserialized one.
	 * @param obj Object to test.
	 * @param equality Whether to test for equality.
	 * @return The deserialized instance.
	 * @throws UnableToSerializeException
	 * @see {@link EqualityTests#two(Object, Object)}
	 */
	public static <T> T check(T obj, boolean equality) throws UnableToSerializeException {
		assertNotNull(obj, "The provided object is null");
		return check(obj, obj.getClass(), equality);
	}

	/**
	 * Checks serializability of one object and equality of the provided instance and the deserialized
	 * one.
	 * @param obj Object to test.
	 * @return The deserialized instance.
	 * @throws UnableToSerializeException
	 * @see {@link EqualityTests#two(Object, Object)}
	 */
	public static <T> T check(T obj) throws UnableToSerializeException {
		return check(obj, true);
	}

}
