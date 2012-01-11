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
package net.derquinse.common.uuid;

import java.util.UUID;

import com.google.common.base.Function;

/**
 * Static utility methods pertaining to {@code UUID} objects.
 * @author Andres Rodriguez
 */
public final class UUIDs {
	/** Not instantiable. */
	private UUIDs() {
		throw new AssertionError();
	}

	/**
	 * Returns a function transforming a string into an UUID. The returned function does not allow
	 * null inputs and throws {@link IllegalArgumentException} for strings not representing a valid
	 * UUID.
	 */
	public static Function<String, UUID> fromString() {
		return FromString.INSTANCE;
	}

	private enum FromString implements Function<String, UUID> {
		INSTANCE;

		/*
		 * (non-Javadoc)
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		public UUID apply(String from) {
			return UUID.fromString(from);
		}

		@Override
		public String toString() {
			return "UUIDs.fromString";
		}
	}

	/**
	 * Returns an UUID parsed from a string.
	 * @see UUID#fromString.
	 * @param uuidString String to parse.
	 * @return The parsed UUID or {@code null} if the argument is {@code null} or unparseable.
	 */
	public static UUID safeFromString(String uuidString) {
		if (uuidString == null) {
			return null;
		}
		try {
			return UUID.fromString(uuidString);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * Returns a function encapsulating the {@link #safeFromString(UUID) safeFromString} method.
	 * @return The {@link #safeFromString(UUID) safeFromString} function.
	 */
	public static Function<String, UUID> safeFromString() {
		return SafeFromString.INSTANCE;
	}

	private enum SafeFromString implements Function<String, UUID> {
		INSTANCE;

		/*
		 * (non-Javadoc)
		 * @see com.google.common.base.Function#apply(java.lang.Object)
		 */
		public UUID apply(String from) {
			return safeFromString(from);
		}

		@Override
		public String toString() {
			return "UUIDs.safeFromString";
		}
	}
}
