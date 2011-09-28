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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.Iterables.filter;
import static net.derquinse.common.i18n.Locales.fromString;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * Utility methods to build localized values.
 * @author Andres Rodriguez
 */
public final class LocalizedValues {
	/**
	 * Not instantiable.
	 */
	private LocalizedValues() {
		throw new AssertionError();
	}

	/**
	 * Builds a localized value.
	 * @param defaultValue Default value.
	 * @param values Localized values map.
	 * @return The localized string.
	 * @throws NullPointerException if the default value or any of the keys or values is {@code null}.
	 */
	public static <T> Localized<T> fromMap(T defaultValue, @Nullable Map<? extends Locale, ? extends T> values) {
		if (values == null || values.isEmpty()) {
			return Unlocalized.of(defaultValue);
		}
		return LocalizedBuilder.create(defaultValue).putAll(values).get();
	}

	/**
	 * Builds a localized map.
	 * @param defaultValue Default value.
	 * @param values Localized values map.
	 * @return The localized string.
	 * @throws NullPointerException if the default value or any of the keys or values is {@code null}.
	 * @throws IllegalArgumentException if unable to parse any of the locales.
	 */
	public static <T> Localized<T> fromStringMap(T defaultValue, @Nullable Map<String, ? extends T> values) {
		if (values == null || values.isEmpty()) {
			return Unlocalized.of(defaultValue);
		}
		final LocalizedBuilder<T> builder = LocalizedBuilder.create(defaultValue);
		for (Entry<String, ? extends T> entry : values.entrySet()) {
			builder.put(fromString(entry.getKey()), entry.getValue());
		}
		return builder.get();
	}

	/**
	 * Parses a collection of {@link L7d} annotations.
	 * @param values Collection. {@code null} elements are filtered out.
	 * @return The parsed locale map.
	 * @throws IllegalArgumentException if unable to parse any of the annotations.
	 */
	public static Map<Locale, String> parse(Iterable<? extends L7d> values) {
		if (values == null) {
			return ImmutableMap.of();
		}
		final Map<Locale, String> map = Maps.newHashMap();
		for (L7d v : filter(values, notNull())) {
			map.put(Locales.fromString(v.locale()), v.value());
		}
		return map;
	}

	/**
	 * Parses an array of {@link L7d} annotations.
	 * @param values Collection. {@code null} elements are filtered out.
	 * @return The parsed locale map.
	 * @throws IllegalArgumentException if unable to parse any of the locales.
	 */
	public static Map<Locale, String> parse(L7d... values) {
		if (values == null || values.length == 0) {
			return ImmutableMap.of();
		}
		return parse(Arrays.asList(values));
	}

	/**
	 * Parses a {@link L7dString} annotation.
	 * @param values Collection. {@code null} elements are filtered out.
	 * @return The parsed localized string or {@code null} if the argument is {@code null}.
	 * @throws IllegalArgumentException if unable to parse any of the locales.
	 */
	public static Localized<String> parse(L7dString value) {
		if (value == null) {
			return null;
		}
		return fromMap(value.value(), parse(value.values()));
	}

	private static <T> T get(Class<?> type, Object object, String method, Class<T> returnType, String error) {
		final Object value;
		try {
			value = type.getMethod(method).invoke(object);
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("Unable to get the %s parameter", error));
		}
		checkArgument(returnType.isInstance(value), "Invalid type of the %s parameter", error);
		return returnType.cast(value);
	}

	/**
	 * Parses a custom annotation.
	 * @param annotation Annotation to parse.
	 * @param value Default value annotation parameter
	 * @param values Localized values annotation parameter
	 * @return The parsed localized string or {@code null} if the annotation is {@code null}.
	 * @throws IllegalArgumentException if unable to parse the annotation.
	 */
	public static Localized<String> parse(Annotation annotation, String value, String values) {
		checkNotNull(value, "The default value annotation parameter must be provided");
		checkNotNull(value, "The localized values annotation parameter must be provided");
		if (annotation == null) {
			return null;
		}
		final Class<? extends Annotation> type = annotation.annotationType();
		final String defaultValue = get(type, annotation, value, String.class, "default value");
		final L7d[] l7ds = get(type, annotation, values, L7d[].class, "localized values");
		return fromMap(defaultValue, parse(l7ds));
	}

	/**
	 * Wraps a localized value with another that provides fallback value.
	 * @param value Localized value to wrap.
	 * @param fallback Fallback value.
	 * @return The localized object or the fallback value if an error occurs (may be {@code null} if
	 *         the provided fallback value is {@code null}).
	 */
	public static <T> Localized<T> withFallback(final Localized<T> value, final T fallback) {
		checkNotNull(value, "The localized value to wrap must be provided");
		checkNotNull(value, "The fallback value must be provided");
		return new ForwardingLocalized<T>() {
			@Override
			public T apply(Locale from) {
				try {
					return delegate().apply(from);
				} catch (UnableToLocalizeException e) {
					return fallback;
				}
			}

			@Override
			public T get() {
				try {
					return delegate().get();
				} catch (UnableToLocalizeException e) {
					return fallback;
				}
			}

			@Override
			protected Localized<T> delegate() {
				return value;
			}

			@Override
			public String toString() {
				return String.format("Localized value [%s] with fallback [%s]", value, fallback);
			}
		};
	}
}
