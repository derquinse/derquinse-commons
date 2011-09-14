/*
 * Copyright 2008-2010 the original author or authors.
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

/**
 * Exception thrown when a sequence is not found by name.
 * @author Andres Rodriguez
 */
public class SequenceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 3523674266399988814L;
	/** Name (id) of the requested sequence. */
	private final String id;

	/**
	 * Initializes the exception.
	 * @param id Name (id) of the requested sequence.
	 */
	public SequenceNotFoundException(final String id) {
		this.id = id;
	}

	/**
	 * Returns the name (id) of the requested sequence.
	 * @return The name (id) of the requested sequence.
	 */
	public String getId() {
		return id;
	}

	@Override
	public String getMessage() {
		return "Sequence not found: [" + id + ']';
	}
}
