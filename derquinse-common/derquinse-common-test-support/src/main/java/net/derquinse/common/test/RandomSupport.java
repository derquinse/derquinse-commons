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

import java.io.ByteArrayInputStream;
import java.security.SecureRandom;

import com.google.common.io.ByteStreams;
import com.google.common.io.InputSupplier;

/**
 * Randomness support class for tests.
 * @author Andres Rodriguez
 */
public final class RandomSupport {
	private static final SecureRandom RANDOM = new SecureRandom();

	/** Not instantiable. */
	private RandomSupport() {
		throw new AssertionError();
	}

	public static int nextInt() {
		return RANDOM.nextInt();
	}

	public static int nextInt(int max) {
		return RANDOM.nextInt(max);
	}

	public static int nextInt(int min, int max) {
		return min + nextInt(max - min);
	}

	public static byte[] getBytes(int amount) {
		byte[] data = new byte[amount];
		RANDOM.nextBytes(data);
		return data;
	}

	public static InputSupplier<ByteArrayInputStream> getSupplier(int amount) {
		return ByteStreams.newInputStreamSupplier(getBytes(amount));
	}
}
