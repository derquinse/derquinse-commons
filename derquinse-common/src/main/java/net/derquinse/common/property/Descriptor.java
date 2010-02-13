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
package net.derquinse.common.property;

import java.lang.reflect.Type;

/**
 * Base interface for type descriptors.
 * @author Andres Rodriguez
 * @param <T> Described type.
 */
public interface Descriptor<T> {
	/**
	 * Returns the type.
	 * @return The type.
	 */
	Type getType();

	/**
	 * Returns the raw type.
	 * @return The raw type.
	 */
	Class<? super T> getRawType();

}
