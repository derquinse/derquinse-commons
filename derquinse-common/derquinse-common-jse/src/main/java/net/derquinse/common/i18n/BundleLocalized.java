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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * A localized objects backed by a resource bundle. For this objects the default value is the value
 * for the default locale.
 * @author Andres Rodriguez
 */
public final class BundleLocalized<T> extends AbstractLocalized<T> {
	/** Default suffix. */
	public static final String SUFFIX = "Bundle";
	/** Bundle base name. */
	private final String baseName;
	/** Bundle key. */
	private final String key;

	/**
	 * Default constructor.
	 * @param baseName Bundle base name.
	 * @param key Bundle key.
	 */
	public BundleLocalized(String baseName, String key) {
		this.baseName = checkNotNull(baseName, "A bundle base name must be provided");
		this.key = checkNotNull(key, "A bundle key must be provided");
	}

	/**
	 * Class-based constructor. The base name is the class name plus the default suffix.
	 * @param type Bundle base type.
	 * @param key Bundle key.
	 */
	public BundleLocalized(Class<?> type, String key) {
		this(checkNotNull(type, "A base type must be provided").getName() + SUFFIX, key);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.i18n.Localized#get(java.util.Locale)
	 */
	public T get(Locale locale) {
		try {
			final ResourceBundle bundle;
			if (locale == null) {
				bundle = ResourceBundle.getBundle(baseName);
			} else {
				bundle = ResourceBundle.getBundle(baseName, locale);
			}
			@SuppressWarnings("unchecked")
			final T value = (T) bundle.getObject(key);
			return value;
		} catch (MissingResourceException e) {
			throw new UnableToLocalizeException(e);
		} catch (ClassCastException e) {
			throw new UnableToLocalizeException(e);
		}
	}
}
