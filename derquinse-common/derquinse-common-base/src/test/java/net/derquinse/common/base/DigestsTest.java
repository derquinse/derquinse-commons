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

import java.security.NoSuchAlgorithmException;

import org.testng.annotations.Test;

/**
 * Tests for Digests
 * @author Andres Rodriguez
 */
public class DigestsTest {
	/**
	 * Creation test.
	 */
	@Test
	public void creation() throws NoSuchAlgorithmException {
		Digests.md5();
		Digests.sha1();
		Digests.sha256();
		Digests.sha512();
	}
}
