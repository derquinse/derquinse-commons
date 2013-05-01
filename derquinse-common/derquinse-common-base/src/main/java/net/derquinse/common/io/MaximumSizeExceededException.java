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
package net.derquinse.common.io;

import java.io.IOException;

/**
 * Exception thrown when the maximum size of the loader is exceeded.
 * @author Andres Rodriguez
 */
public final class MaximumSizeExceededException extends IOException {
	/** Serial UID. */
	private static final long serialVersionUID = 2406621390826357905L;

	/** Maximum size. */
	private final int maxSize;

	/** Constructor. */
	public MaximumSizeExceededException(int maxSize) {
		super(String.format("Maximum size of %d bytes exceeded", maxSize));
		this.maxSize = InternalPreconditions.checkMaxSize(maxSize);
	}

	/** Returns the maximum size. */
	public int getMaxSize() {
		return maxSize;
	}
}