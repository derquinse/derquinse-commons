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

/**
 * Exception thrown when an error occurs during serialization tests.
 * @author Andres Rodriguez
 */
public class UnableToSerializeException extends RuntimeException {
	/** Serial UID. */
	private static final long serialVersionUID = -8495842771421658576L;

	/** Constructor. */
	public UnableToSerializeException(Throwable cause) {
		super(cause);
	}
}
