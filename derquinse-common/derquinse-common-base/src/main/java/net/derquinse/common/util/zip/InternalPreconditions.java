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
package net.derquinse.common.util.zip;

import static com.google.common.base.Preconditions.checkNotNull;
import net.derquinse.common.base.NotInstantiable;
import net.derquinse.common.io.MemoryByteSourceLoader;

/**
 * Preconditions for the current package.
 * @author Andres Rodriguez
 */
final class InternalPreconditions extends NotInstantiable {
	/** Not instantiable. */
	private InternalPreconditions() {
	}

	/** Checks the input argument is provided. */
	static <T> T checkInput(T input) {
		return checkNotNull(input, "The input argument must be provided");
	}

	/** Checks the output argument is provided. */
	static <T> T checkOutput(T output) {
		return checkNotNull(output, "The output argument must be provided");
	}

	/** Checks a {@link MemoryByteSourceLoader} is provided. */
	static MemoryByteSourceLoader checkLoader(MemoryByteSourceLoader loader) {
		return checkNotNull(loader, "The memory byte source loader must be provided");
	}
}
