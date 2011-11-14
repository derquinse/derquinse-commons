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
package net.derquinse.common.base;

import java.security.MessageDigest;
import java.security.SecureRandom;

import net.derquinse.common.test.EqualityTests;
import net.derquinse.common.test.HessianSerializabilityTests;
import net.derquinse.common.test.SerializabilityTests;

import org.testng.annotations.Test;

/**
 * Tests for ByteString
 * @author Andres Rodriguez
 */
public class ByteStringTest {
	private static final SecureRandom R = new SecureRandom();

	private ByteString createRandom() {
		final MessageDigest d = Digests.sha1();
		final byte[] bytes = new byte[2048];
		for (int i = 0; i < 1500; i++) {
			R.nextBytes(bytes);
			d.update(bytes);
		}
		return ByteString.copyFrom(d.digest());
	}

	/**
	 * Simple test.
	 */
	@Test
	public void simple() throws Exception {
		ByteString s1 = createRandom();
		String s = s1.toHexString();
		System.out.println();
		ByteString s2 = ByteString.fromHexString(s);
		EqualityTests.two(s1, s2);
	}

	/**
	 * Serialization.
	 */
	@Test
	public void serialization() throws Exception {
		ByteString s = createRandom();
		SerializabilityTests.check(s);
		HessianSerializabilityTests.both(s);
	}

}
