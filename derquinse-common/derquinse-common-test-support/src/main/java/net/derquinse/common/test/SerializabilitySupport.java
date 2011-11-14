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

import java.io.Serializable;

/**
 * Support class for serializability support methods.
 * @author Andres Rodriguez
 */
class SerializabilitySupport {
	/** Not instantiable. */
	private SerializabilitySupport() {
		throw new AssertionError();
	}

	/**
	 * Casts a deserialized object and checks (if desired) equality of the provided instance and the
	 * deserialized one.
	 * @param obj Object to test.
	 * @param deserialized Instance
	 * @param equality Whether to test for equality.
	 * @return The deserialized instance with the correct type.
	 * @throws UnableToSerializeException if unable to cast the object
	 * @see {@link EqualityTests#two(Object, Object)}
	 */
	static <T extends Serializable> T cast(T obj, Object deserialized, boolean equality)
			throws UnableToSerializeException {
		assertNotNull(obj, "The provided object to serialize is null");
		try {
			@SuppressWarnings("unchecked")
			final T d = (T) obj.getClass().cast(deserialized);
			if (equality) {
				EqualityTests.two(obj, deserialized);
			} else {
				assertNotNull(deserialized, "The deserialized object is null");
			}
			return d;
		} catch (ClassCastException e) {
			throw new UnableToSerializeException(e);
		}
	}
}
