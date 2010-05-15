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
package net.derquinse.common.i18n;

import java.util.Locale;

import net.derquinse.common.base.ForwardingFunction;

/**
 * An abstract base class for implementing the <a
 * href="http://en.wikipedia.org/wiki/Decorator_pattern">decorator pattern</a> for localized values.
 * The {@link #delegate()} method must be overridden to return the instance being decorated.
 * @param <T> Localized value type.
 * @author Andres Rodriguez
 */
public abstract class ForwardingLocalized<T> extends ForwardingFunction<Locale, T> implements Localized<T> {
	/**
	 * Returns the backing delegate instance that methods are forwarded to.
	 * @return The delegate instance.
	 */
	protected abstract Localized<T> delegate();

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.i18n.Localized#get()
	 */
	public T get() {
		return delegate().get();
	}
}
