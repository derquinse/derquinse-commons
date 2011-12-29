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
package net.derquinse.common.meta;

import java.util.regex.Pattern;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 * Class for string-valued property descriptor.
 * @author Andres Rodriguez
 * @param <C> Containing type.
 */
public abstract class StringMetaProperty<C> extends ComparableMetaProperty<C, String> {
	/**
	 * Default constructor.
	 * @param name Property name.
	 * @param required True if the property is required.
	 * @param validity Validity predicate.
	 * @param defaultValue Default value for the property.
	 */
	protected StringMetaProperty(String name, boolean required, @Nullable Predicate<? super String> validity,
			@Nullable String defaultValue) {
		super(name, required, validity, defaultValue);
	}

	/**
	 * Constructor.
	 * @param name Property name.
	 * @param required True if the property is required.
	 * @param validity Validity predicate.
	 */
	protected StringMetaProperty(String name, boolean required, @Nullable Predicate<? super String> validity) {
		super(name, required, validity);
	}

	/**
	 * Constructor.
	 * @param name Property name.
	 * @param required True if the property is required.
	 */
	protected StringMetaProperty(String name, boolean required) {
		super(name, required);
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the property value being tested contains
	 * any match for the given regular expression pattern. The test used is equivalent to
	 * {@code Pattern.compile(pattern).matcher(arg).find()}
	 * @throws java.util.regex.PatternSyntaxException if the pattern is invalid
	 */
	public final Predicate<C> containsPattern(String pattern) {
		return compose(Predicates.containsPattern(pattern));
	}

	/**
	 * Returns a predicate that evaluates to {@code true} if the property value being tested contains
	 * any match for the given regular expression pattern. The test used is equivalent to
	 * {@code regex.matcher(arg).find()}
	 */
	public final Predicate<C> contains(Pattern pattern) {
		return compose(Predicates.contains(pattern));
	}

}
