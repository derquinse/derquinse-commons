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
package net.derquinse.common.base;

/**
 * Marker class for not instantiable objects. Subclasses are recommended to have private
 * constructors an be final.
 * @author Andres Rodriguez
 */
public abstract class NotInstantiable {
	/**
	 * Constructor.
	 * @throws AssertionError if called.
	 */
	protected NotInstantiable() {
		throw new AssertionError(String.format("Class %s is not instantiable"));
	}
}
