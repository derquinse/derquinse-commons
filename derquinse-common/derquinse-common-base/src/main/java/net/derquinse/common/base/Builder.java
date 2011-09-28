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
 * A class that builds objects of a certain type.
 * @param <T> the type of the created object.
 * @author Andres Rodriguez
 */
public interface Builder<T> {
	/**
	 * Returns the built object. Semantically a new instance is created but in case of immutable
	 * objects instance reuse is allowed.
	 */
	T build();
}
