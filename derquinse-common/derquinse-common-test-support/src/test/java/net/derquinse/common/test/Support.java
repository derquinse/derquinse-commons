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

/**
 * Support class for tests.
 * @author Andres Rodriguez
 */
final class Support {
	/** Not instantiable. */
	private Support() {
		throw new AssertionError();
	}

	/**
	 * Checks a required object is provided.
	 */
	static <T> T required(T obj) {
		assertNotNull(obj, "The provided object is null");
		return obj;
	}
}
