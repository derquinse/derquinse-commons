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
package net.derquinse.common.orm;

import net.derquinse.common.base.NotInstantiable;

/**
 * Common lengths values used for properties in ORM mappings.
 * @author Andres Rodriguez
 */
public final class PropertyLengths extends NotInstantiable {
	/** Not instantiable. */
	private PropertyLengths() {
	}

	/** UUID in bytes. */
	public static final int UUID_BYTES = 16;
	/** UUID in chars. */
	public static final int UUID_CHARS = 36;
	/** MD5 hash in bytes. */
	public static final int MD5_BYTES = 16;
	/** SHA-1 hash in bytes. */
	public static final int SHA1_BYTES = 20;
	/** SHA-224 hash in bytes. */
	public static final int SHA224_BYTES = 28;
	/** SHA-256 hash in bytes. */
	public static final int SHA256_BYTES = 32;
	/** SHA-384 hash in bytes. */
	public static final int SHA384_BYTES = 48;
	/** SHA-512 hash in bytes. */
	public static final int SHA512_BYTES = 64;
	/** Locale in chars. */
	public static final int LOCALE_CHARS = 100;
}
