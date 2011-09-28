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

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.ForwardingObject;

/**
 * An abstract base class for implementing the <a
 * href="http://en.wikipedia.org/wiki/Decorator_pattern">decorator pattern</a> for functions. The
 * {@link #delegate()} method must be overridden to return the instance being decorated.
 * @param <F> the type of the function input
 * @param <T> the type of the function output
 * @author Andres Rodriguez
 */
public abstract class ForwardingFunction<F, T> extends ForwardingObject implements Function<F, T> {
	/**
	 * Returns the backing delegate instance that methods are forwarded to.
	 * @return The delegate instance.
	 */
	protected abstract Function<F, T> delegate();

	/*
	 * (non-Javadoc)
	 * @see com.google.common.base.Function#apply(java.lang.Object)
	 */
	public T apply(@Nullable F from) {
		return delegate().apply(from);
	};
}
