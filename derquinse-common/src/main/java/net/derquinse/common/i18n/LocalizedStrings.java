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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.Iterables.filter;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * Utility methods to build localized string.
 * @author Andres Rodriguez
 */
public final class LocalizedStrings {
	/**
	 * Not instantiable.
	 */
	private LocalizedStrings() {
		throw new AssertionError();
	}

	/**
	 * Builds a localized map.
	 * @param defaultValue Default value.
	 * @param values Localized values map.
	 * @return The localized string.
	 * @throws NullPointerException if the default value or any of the keys or values is {@code null}.
	 */
	public static Localized<String> fromLocaleMap(String defaultValue, Map<? extends Locale, String> values) {
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
	public static Localized<String> fromStringMap(String defaultValue, Map<String, String> values) {
		return LocalizedBuilder.create(defaultValue).putAllStrings(values).get();
	}

	/**
	 * Parses a collection of {@see L7d} annotations.
	 * @param values Collection. {@code null} elements are filtered out.
	 * @return The parsed locale map.
	 * @throws IllegalArgumentException if unable to parse any of the locales.
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
	 * Parses a collection of {@see L7d} annotations.
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
	 * Parses a {@see L7dString} annotation.
	 * @param values Collection. {@code null} elements are filtered out.
	 * @return The parsed localized string or {@code null} if the argument is {@code null}.
	 * @throws IllegalArgumentException if unable to parse any of the locales.
	 */
	public static Localized<String> parse(L7dString value) {
		if (value == null) {
			return null;
		}
		return fromLocaleMap(value.value(), parse(value.values()));
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
		return fromLocaleMap(defaultValue, parse(l7ds));
	}

}
