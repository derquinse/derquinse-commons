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

import static org.testng.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Serializability support methods.
 * @author Andres Rodriguez
 */
public final class SerializabilityTests {
	/** Not instantiable. */
	private SerializabilityTests() {
		throw new AssertionError();
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
	public static <T extends Serializable> T check(T obj, boolean equality) throws UnableToSerializeException {
		assertNotNull(obj, "The provided serializable object is null");
		final Object deserialized;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(2048);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.close();
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			deserialized = ois.readObject();
		} catch (Throwable t) {
			throw new UnableToSerializeException(t);
		}
		return SerializabilitySupport.cast(obj, deserialized, equality);
	}

	/**
	 * Checks serializability of one object and equality of the provided instance and the deserialized
	 * one.
	 * @param obj Object to test.
	 * @return The deserialized instance.
	 * @throws UnableToSerializeException
	 * @see {@link EqualityTests#two(Object, Object)}
	 */
	public static <T extends Serializable> T check(T obj) throws UnableToSerializeException {
		return check(obj, true);
	}

}
