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
import java.io.Serializable;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

/**
 * Hessian serializability support methods.
 * @author Andres Rodriguez
 */
public final class HessianSerializabilityTests {
	/** Not instantiable. */
	private HessianSerializabilityTests() {
		throw new AssertionError();
	}

	/**
	 * Checks serializability of one object and (if desired) equality of the provided instance and the
	 * deserialized one using Hessian 1.
	 * @param obj Object to test.
	 * @param equality Whether to test for equality.
	 * @return The deserialized instance.
	 * @throws UnableToSerializeException
	 * @see {@link EqualityTests#two(Object, Object)}
	 */
	public static <T extends Serializable> T hessian1(T obj, boolean equality) throws UnableToSerializeException {
		assertNotNull(obj, "The provided serializable object is null");
		final Object deserialized;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(2048);
			HessianOutput ho = new HessianOutput(bos);
			ho.writeObject(obj);
			ho.close();
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			HessianInput ois = new HessianInput(bis);
			deserialized = ois.readObject();
		} catch (Throwable t) {
			throw new UnableToSerializeException(t);
		}
		return SerializabilitySupport.cast(obj, deserialized, equality);
	}

	/**
	 * Checks serializability of one object and equality of the provided instance and the deserialized
	 * one using Hessian 1.
	 * @param obj Object to test.
	 * @return The deserialized instance.
	 * @throws UnableToSerializeException
	 * @see {@link EqualityTests#two(Object, Object)}
	 */
	public static <T extends Serializable> T hessian1(T obj) throws UnableToSerializeException {
		return hessian1(obj, true);
	}

	/**
	 * Checks serializability of one object and (if desired) equality of the provided instance and the
	 * deserialized one using Hessian 2.
	 * @param obj Object to test.
	 * @param equality Whether to test for equality.
	 * @return The deserialized instance.
	 * @throws UnableToSerializeException
	 * @see {@link EqualityTests#two(Object, Object)}
	 */
	public static <T extends Serializable> T hessian2(T obj, boolean equality) throws UnableToSerializeException {
		assertNotNull(obj, "The provided serializable object is null");
		final Object deserialized;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(2048);
			Hessian2Output ho = new Hessian2Output(bos);
			ho.writeObject(obj);
			ho.close();
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			Hessian2Input ois = new Hessian2Input(bis);
			deserialized = ois.readObject();
		} catch (Throwable t) {
			throw new UnableToSerializeException(t);
		}
		return SerializabilitySupport.cast(obj, deserialized, equality);
	}

	/**
	 * Checks serializability of one object and equality of the provided instance and the deserialized
	 * one using Hessian 2.
	 * @param obj Object to test.
	 * @return The deserialized instance.
	 * @throws UnableToSerializeException
	 * @see {@link EqualityTests#two(Object, Object)}
	 */
	public static <T extends Serializable> T hessian2(T obj) throws UnableToSerializeException {
		return hessian2(obj, true);
	}

	/**
	 * Checks serializability of one object and (if desired) equality of the provided instance and the
	 * deserialized one using both Hessian 1 and 2.
	 * @param obj Object to test.
	 * @param equality Whether to test for equality.
	 * @throws UnableToSerializeException
	 * @see {@link EqualityTests#two(Object, Object)}
	 */
	public static void both(Serializable obj, boolean equality) throws UnableToSerializeException {
		hessian1(obj, equality);
		hessian2(obj, equality);
	}

	/**
	 * Checks serializability of one object and equality of the provided instance and the deserialized
	 * one using both Hessian 1 and 2.
	 * @param obj Object to test.
	 * @throws UnableToSerializeException
	 * @see {@link EqualityTests#two(Object, Object)}
	 */
	public static void both(Serializable obj) throws UnableToSerializeException {
		hessian1(obj);
		hessian2(obj);
	}

}
