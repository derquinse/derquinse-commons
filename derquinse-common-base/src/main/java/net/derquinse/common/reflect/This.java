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
package net.derquinse.common.reflect;

import static com.google.common.base.Preconditions.checkArgument;
import static net.derquinse.common.reflect.Types.getSuperclassTypeArgument;

/**
 * Base class for classes that make reference to themselves' types.
 * @author Andres Rodriguez
 * @param <T> This type.
 */
public abstract class This<T extends This<T>> {
	/**
	 * Constructor.
	 * @throws IllegalArgumentException if the type argument is not the same as the actual type.
	 */
	public This() {
		final Class<?> type = getClass();
		checkArgument(type.equals(getSuperclassTypeArgument(type)));
	}

	/**
	 * Returns the "this" value.
	 * @return This object.
	 */
	@SuppressWarnings("unchecked")
	public final T thisValue() {
		return (T) this;
	}
}
