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

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

/**
 * Generic interface for localized objects. Target objects must be non-null. If a localized object
 * is optional, it is the "Localized" reference the one that must be null, and not the target
 * object.
 * @author Andres Rodriguez
 * @param <T> Localized object type.
 */
public interface Localized<T> extends Supplier<T>, Function<Locale, T> {
	/**
	 * Returns the object for the specified locale. If no value for the specified locale is found or
	 * the argument is {@code null}, the default value must be returned. The returned value must be
	 * non-null.
	 * @param locale The requested locale.
	 * @return The localized object.
	 * @throws UnableToLocalizeException if an error occurs.
	 */
	T apply(@Nullable Locale locale);

	/**
	 * Returns the default value for the localized object. The returned value must be non-null. Note
	 * that the default value may not be the value for the systems default locale.
	 * @return The localized object's default value.
	 * @throws UnableToLocalizeException if an error occurs.
	 */
	T get();
}
