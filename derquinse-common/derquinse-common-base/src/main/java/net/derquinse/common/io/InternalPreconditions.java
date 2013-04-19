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

import static com.google.common.base.Preconditions.checkArgument;
import net.derquinse.common.base.NotInstantiable;

/**
 * Package private preconditions.
 * @author Andres Rodriguez
 */
final class InternalPreconditions extends NotInstantiable {
	/** Constructor. */
	private InternalPreconditions() {
	}

	/** Checks that a chunk size is greater than zero, returning the checked value. */
	static int checkChunkSize(int chunkSize) {
		checkArgument(chunkSize > 0, "The chunk size must be > 0");
		return chunkSize;
	}

	/** Checks that a maximum size is greater than zero, returning the checked value. */
	static int checkMaxSize(int maxSize) {
		checkArgument(maxSize > 0, "The maximum size must be > 0");
		return maxSize;
	}

	/**
	 * Checks that a size >= 0 and is smaller than or equal to maximum size, returning the checked
	 * value.
	 */
	static int checkSize(int size, int maxSize) {
		checkArgument(size >= 0, "The size must be >= 0");
		if (size > checkMaxSize(maxSize)) {
			throw new IllegalArgumentException(String.format("Size %d greater than maximum %d", size, maxSize));
		}
		return size;
	}

}