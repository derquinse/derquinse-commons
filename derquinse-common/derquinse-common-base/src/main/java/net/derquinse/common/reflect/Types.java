/*
 * Copyright (C) the original author or authors.
 * Copyright 2008 Google Inc.  All rights reserved.
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

package net.derquinse.common.reflect;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.derquinse.common.base.NotInstantiable;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

/**
 * Static methods for working with types.
 * @author Andres Rodriguez
 */
public final class Types extends NotInstantiable {
	private Types() {
	}

	/**
	 * Returns a type token modelling a {@link List} whose elements are of type {@code elementType}.
	 * @return a {@link TypeToken type token} representing the parameterized type.
	 */
	@SuppressWarnings("serial")
	public static <T> TypeToken<List<T>> listOf(Class<T> elementType) {
		return new TypeToken<List<T>>() {}.where(new TypeParameter<T>() {}, elementType);
	}

	/**
	 * Returns a type modelling a {@link Set} whose elements are of type {@code elementType}.
	 * @return a {@link TypeToken type token} representing the parameterized type.
	 */
	@SuppressWarnings("serial")
	public static <T> TypeToken<Set<T>> setOf(Class<T> elementType) {
		return new TypeToken<Set<T>>() {}.where(new TypeParameter<T>() {}, elementType);
	}

	/**
	 * Returns a type modelling a {@link Map} whose keys are of type {@code keyType} and whose values
	 * are of type {@code valueType}.
	 * @return a {@link TypeToken type token} representing the parameterized type.
	 */
	@SuppressWarnings("serial")
	public static <K, V> TypeToken<Map<K, V>> mapOf(Class<K> keyType, Class<V> valueType) {
		return new TypeToken<Map<K, V>>() {}.where(new TypeParameter<K>() {}, keyType).where(new TypeParameter<V>() {},
				valueType);
	}
}