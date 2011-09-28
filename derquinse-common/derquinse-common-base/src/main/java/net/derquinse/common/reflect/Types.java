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

import static com.google.common.base.Preconditions.checkArgument;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Static methods for working with types.
 * @author crazybob@google.com (Bob Lee)
 * @author Andres Rodriguez
 */
public final class Types {
	private Types() {
	}

	/**
	 * Returns a new parameterized type, applying {@code typeArguments} to {@code rawType}. The
	 * returned type does not have an owner type.
	 * @return a {@link java.io.Serializable serializable} parameterized type.
	 */
	public static ParameterizedType newParameterizedType(Type rawType, Type... typeArguments) {
		return newParameterizedTypeWithOwner(null, rawType, typeArguments);
	}

	/**
	 * Returns a new parameterized type, applying {@code typeArguments} to {@code rawType} and
	 * enclosed by {@code ownerType}.
	 * @return a {@link java.io.Serializable serializable} parameterized type.
	 */
	public static ParameterizedType newParameterizedTypeWithOwner(Type ownerType, Type rawType, Type... typeArguments) {
		return new MoreTypes.ParameterizedTypeImpl(ownerType, rawType, typeArguments);
	}

	/**
	 * Returns an array type whose elements are all instances of {@code componentType}.
	 * @return a {@link java.io.Serializable serializable} generic array type.
	 */
	public static GenericArrayType arrayOf(Type componentType) {
		return new MoreTypes.GenericArrayTypeImpl(componentType);
	}

	/**
	 * Returns a type that represents an unknown type that extends {@code bound}. For example, if
	 * {@code bound} is {@code CharSequence.class}, this returns {@code ? extends CharSequence}. If
	 * {@code bound} is {@code Object.class}, this returns {@code ?}, which is shorthand for
	 * {@code ? extends Object}.
	 */
	public static WildcardType subtypeOf(Type bound) {
		return new MoreTypes.WildcardTypeImpl(new Type[] { bound }, MoreTypes.EMPTY_TYPE_ARRAY);
	}

	/**
	 * Returns a type that represents an unknown supertype of {@code bound}. For example, if
	 * {@code bound} is {@code String.class}, this returns {@code ?
	 * super String}.
	 */
	public static WildcardType supertypeOf(Type bound) {
		return new MoreTypes.WildcardTypeImpl(new Type[] { Object.class }, new Type[] { bound });
	}

	/**
	 * Returns a type modelling a {@link List} whose elements are of type {@code elementType}.
	 * @return a {@link java.io.Serializable serializable} parameterized type.
	 */
	public static ParameterizedType listOf(Type elementType) {
		return newParameterizedType(List.class, elementType);
	}

	/**
	 * Returns a type modelling a {@link Set} whose elements are of type {@code elementType}.
	 * @return a {@link java.io.Serializable serializable} parameterized type.
	 */
	public static ParameterizedType setOf(Type elementType) {
		return newParameterizedType(Set.class, elementType);
	}

	/**
	 * Returns a type modelling a {@link Map} whose keys are of type {@code keyType} and whose values
	 * are of type {@code valueType}.
	 * @return a {@link java.io.Serializable serializable} parameterized type.
	 */
	public static ParameterizedType mapOf(Type keyType, Type valueType) {
		return newParameterizedType(Map.class, keyType, valueType);
	}

	/**
	 * Returns the actual type arguments of the generic superclass of the provided class.
	 * @param subclass Class to process.
	 * @return The type arguments of the generic superclass.
	 * @throws IllegalArgumentException if the superclass is not a parametrized type.
	 */
	public static Type[] getSuperclassTypeArguments(Class<?> subclass) {
		final Type superclass = subclass.getGenericSuperclass();
		checkArgument(superclass instanceof ParameterizedType, "Missing type parameter.");
		final ParameterizedType parameterized = (ParameterizedType) superclass;
		return parameterized.getActualTypeArguments();
	}

	/**
	 * Returns the first actual type argument of the generic superclass of the provided class.
	 * @param subclass Class to process.
	 * @return The first type argument of the generic superclass.
	 * @throws IllegalArgumentException if the superclass is not a parametrized type.
	 */
	public static Type getSuperclassTypeArgument(Class<?> subclass) {
		return getSuperclassTypeArguments(subclass)[0];
	}

	/**
	 * Returns the raw type corresponding to the provided type.
	 * @param type Type.
	 * @return The raw type.
	 */
	public static Class<?> getRawType(Type type) {
		return MoreTypes.getRawType(type);
	}

}