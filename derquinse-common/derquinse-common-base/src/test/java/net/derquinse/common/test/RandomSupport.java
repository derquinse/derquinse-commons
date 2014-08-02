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

import java.security.SecureRandom;

import com.google.common.io.ByteSource;

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

	/**
	 * Returns a pseudorandom, uniformly distributed {@code int}.
	 */
	public static int nextInt() {
		return RANDOM.nextInt();
	}

	/**
	 * Returns a pseudorandom, uniformly distributed {@code int} value between 0 (inclusive) and the
	 * specified value (exclusive).
	 */
	public static int nextInt(int max) {
		return RANDOM.nextInt(max);
	}

	/**
	 * Returns a pseudorandom, uniformly distributed {@code int} value between the {@code min}
	 * argument (inclusive) and the {@code max} argument (exclusive).
	 */
	public static int nextInt(int min, int max) {
		return min + nextInt(max - min);
	}

	/**
	 * Generates a user-specified number of random bytes.
	 */
	public static byte[] getBytes(int amount) {
		byte[] data = new byte[amount];
		RANDOM.nextBytes(data);
		return data;
	}

	/**
	 * Generates a user-specified number of random bytes, returning them as a byte source.
	 */
	public static ByteSource getSource(int amount) {
		return ByteSource.wrap(getBytes(amount));
	}
}
