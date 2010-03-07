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

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

/**
 * Utility methods for Locales.
 * @author Andres Rodriguez
 */
public final class Locales {
	/** Static method fromString as a function. */
	public static final Function<String, Locale> FROM_STRING = new Function<String, Locale>() {
		public Locale apply(String from) {
			return fromString(from);
		}
	};
	
	/**
	 * Not instantiable.
	 */
	private Locales() {
		throw new AssertionError();
	}

	private static String clean(String string) {
		if (string == null || string.length() == 0) {
			return null;
		}
		return string;
	}

	/**
	 * Returns the parent of the provided locale.
	 * @param locale Provided locale.
	 * @return The parent locale or {@code null} if the provided locale is
	 *         {@code null} or the provided locale has only language component.
	 */
	public static Locale getParent(Locale locale) {
		if (locale == null) {
			return null;
		}
		final String lang = clean(locale.getLanguage());
		final String country = clean(locale.getCountry());
		final String variant = clean(locale.getVariant());
		if (variant != null) {
			if (country != null) {
				return new Locale(lang, country);
			} else {
				return new Locale(lang);
			}
		} else if (country != null) {
			return new Locale(lang);
		}
		return null;
	}
	
	/**
	 * Returns a locale parsed from a string.
	 * Allowed values are:
	 * <ul>
	 * <li>language</li>
	 * <li>language_country</li>
	 * <li>language_country_variant</li>
	 * </ul>
	 * @param localeString String to parse.
	 * @return The parsed locale.
	 * @throws NullPointerException if the argument is {@code null}.
	 * @throws IllegalArgumentException if unable to parse the argument.
	 */
	public static Locale fromString(String localeString) {
		Preconditions.checkNotNull(localeString);
		Preconditions.checkArgument(localeString.length() > 0);
		String[] split = localeString.split("_");
		final int n = split.length;
		if ( n == 1 ) {
			return new Locale(split[0]);
		} else if (n == 2) {
			return new Locale(split[0], split[1]);
		} else if (n == 3) {
			return new Locale(split[0], split[1], split[2]);
		}
		throw new IllegalArgumentException("Unable to parse locale [" + localeString + ']');
	}

	/**
	 * Returns a locale parsed from a string.
	 * @see fromString.
	 * @param localeString String to parse.
	 * @return The parsed locale or {@code null} if the argument is {@code null} or unparseable.
	 */
	public static Locale safeFromString(String localeString) {
		if (localeString == null) {
			return null;
		}
		try {
			return fromString(localeString);
		} catch(IllegalArgumentException e) {
			return null;
		}
	}
	
}
