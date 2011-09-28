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
package net.derquinse.common.reflect;

import net.derquinse.common.base.Builder;

/**
 * Base class for covariant builders.
 * @author Andres Rodriguez
 * @param <B> Builder type.
 * @param <T> Built type.
 */
public abstract class CovariantBuilder<B extends CovariantBuilder<B, T>, T> extends This<B> implements Builder<T> {
	/**
	 * Constructs a new builder.
	 */
	protected CovariantBuilder() {
	}
}
