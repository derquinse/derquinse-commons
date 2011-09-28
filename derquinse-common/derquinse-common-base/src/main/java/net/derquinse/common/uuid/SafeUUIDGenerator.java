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

import com.google.common.base.Preconditions;

/**
 * Default safe UUID generator. An UUID is generated with the provided generator. In case of any
 * exception, a random UUID is generated.
 * @author Andres Rodriguez
 */
public class SafeUUIDGenerator implements UUIDGenerator {
	/** Generator to use. */
	private final UUIDGenerator generator;

	public SafeUUIDGenerator(final UUIDGenerator generator) {
		Preconditions.checkNotNull(generator, "The provided generator must be non-null.");
		this.generator = generator;
	}

	public UUID get() {
		try {
			return generator.get();
		} catch (Exception e) {
			return UUID.randomUUID();
		}
	}
}
