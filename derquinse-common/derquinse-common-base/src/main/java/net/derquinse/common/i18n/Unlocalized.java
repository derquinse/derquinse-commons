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
package net.derquinse.common.i18n;

import java.util.Locale;

/**
 * Adapter for language-independent or unlocalized values.
 * @author Andres Rodriguez
 * @param <T> Localized value type.
 */
public final class Unlocalized<T> implements Localized<T> {
	private final T value;

	/**
	 * Returns an unlocalized value.
	 * @param <T> Value type.
	 * @param value Value to adapt.
	 * @return The unlocalized value or {@code null} if the argument is {@code null}.
	 */
	public static <T> Unlocalized<T> of(T value) {
		if (value == null) {
			return null;
		} else {
			return new Unlocalized<T>(value);
		}
	}

	/**
	 * Default constructor.
	 * @param value Value.
	 */
	private Unlocalized(T value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.i18n.Localized#get()
	 */
	public T get() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.i18n.Localized#apply(java.util.Locale)
	 */
	public T apply(Locale locale) {
		return value;
	}

}
