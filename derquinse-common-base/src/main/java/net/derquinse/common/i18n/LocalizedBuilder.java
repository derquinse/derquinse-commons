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
import static com.google.common.base.Preconditions.checkState;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * A localized value builder.
 * @author Andres Rodriguez
 * @param <T> Localized object type.
 */
public final class LocalizedBuilder<T> implements Supplier<Localized<T>> {
	/** Null default error. */
	private static final String NULL_DEFAULT = "Default value can't be null";
	/** Null locale error. */
	private static final String NULL_LOCALE = "Null locales not allowed";
	/** Null value error. */
	private static final String NULL_VALUE = "Null values not allowed";
	/** Default value. */
	private T defaultValue;
	/** Localized values. */
	private final Map<Locale, T> values = Maps.newHashMap();

	/**
	 * Creates a new builder.
	 * @return A newly created builder.
	 */
	public static <T> LocalizedBuilder<T> create() {
		return new LocalizedBuilder<T>();
	}

	/**
	 * Creates a new builder.
	 * @param defaultValue Default value.
	 * @return A newly created builder.
	 * @throws NullPointerException if the default value is {@code null}.
	 */
	public static <T> LocalizedBuilder<T> create(T defaultValue) {
		return new LocalizedBuilder<T>().setDefault(defaultValue);
	}

	/**
	 * Default constructor. Use static factory to avoid generic parameter.
	 */
	private LocalizedBuilder() {
	}

	/**
	 * Sets the default value.
	 * @param value Default value.
	 * @return This builder.
	 * @throws NullPointerException if the default value is null.
	 */
	public LocalizedBuilder<T> setDefault(final T value) {
		this.defaultValue = checkNotNull(value, NULL_DEFAULT);
		return this;
	}

	/**
	 * Puts the value for a locale.
	 * @param locale Locale.
	 * @param value Value.
	 * @return This builder.
	 * @throws NullPointerException if any of the arguments is null.
	 */
	public LocalizedBuilder<T> put(Locale locale, T value) {
		checkNotNull(locale, NULL_LOCALE);
		checkNotNull(value, NULL_VALUE);
		values.put(locale, value);
		return this;
	}

	/**
	 * Puts all the localized values from the argument map.
	 * @param values Localized values to add.
	 * @return This builder.
	 * @throws NullPointerException if any of the keys or values is null.
	 */
	public LocalizedBuilder<T> putAll(Map<? extends Locale, ? extends T> values) {
		if (values == null || values.isEmpty()) {
			return this; // nothing to do
		}
		// Check preconditions
		for (Entry<? extends Locale, ? extends T> entry : values.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
		return this;
	}

	/**
	 * Returns the localized value.
	 * @return The localized value.
	 * @throws IllegalStateException if the default value is null.
	 */
	public Localized<T> get() {
		checkState(defaultValue != null, NULL_DEFAULT);
		if (values.isEmpty()) {
			return Unlocalized.of(defaultValue);
		}
		return new LocalizedValue<T>(defaultValue, values);
	}

	private static final class LocalizedValue<T> extends AbstractLocalized<T> {
		private final T defaultValue;
		private final ImmutableMap<Locale, T> values;

		/**
		 * Default constructor.
		 * @param pattern Pattern.
		 * @param args Arguments.
		 */
		private LocalizedValue(T defaultValue, Map<Locale, T> values) {
			this.defaultValue = defaultValue;
			this.values = ImmutableMap.copyOf(values);
		}

		/*
		 * (non-Javadoc)
		 * @see net.derquinse.common.i18n.Localized#get()
		 */
		public T get() {
			return defaultValue;
		}

		/*
		 * (non-Javadoc)
		 * @see net.derquinse.common.i18n.Localized#apply(java.util.Locale)
		 */
		public T apply(Locale locale) {
			while (locale != null) {
				T value = values.get(locale);
				if (value != null) {
					return value;
				}
				locale = Locales.getParent(locale);
			}
			return defaultValue;
		}
	}

}
