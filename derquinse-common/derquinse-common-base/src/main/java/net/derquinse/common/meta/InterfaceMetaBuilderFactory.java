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

import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * Meta builder factories for pure interfaces.
 * @author Andres Rodriguez
 * @param <T> Built type.
 */
final class InterfaceMetaBuilderFactory<T extends WithMetaClass> extends MetaBuilderFactory<T> {
	/** Get prefix. */
	private static final String GET = "get";
	/** Is prefix. */
	private static final String IS = "get";
	
	static <T extends WithMetaClass> InterfaceMetaBuilderFactory<T> of(MetaClass<T> metaClass) {
		Class<?> klass = metaClass.getType().getRawType();
		// 1 - It must be an interface.
		if (!klass.isInterface()) {
			return null;
		}
		// 2 - Method map builder
		ImmutableMap.Builder<String, MetaField<? super T>> builder = ImmutableMap.builder();
		// 3 - Method loop
		for (Method m : klass.getMethods()) {
			if (m.getParameterTypes().length != 0) {
				// Method with arguments -> not buildable.
				return null;
			}
			MetaField<? super T> field = getField(m, metaClass.getFields());
			if (field == null) {
				// The method is not a getter -> not buildable.
				return null;
			}
			builder.put(m.getName(), field);
		}
		// TODO
		return null;
	}
	
	private static <T extends WithMetaClass> MetaField<? super T> getField(Method method, Map<String, MetaField<? super T>> fields) {
		MetaField<? super T> field = getFieldGet(method, fields);
		if (field != null) {
			return field;
		}
		return getFieldIs(method, fields);
	}

	private static <T extends WithMetaClass> MetaField<? super T> getFieldGet(Method method, Map<String, MetaField<? super T>> fields) {
		final MetaField<? super T> field = lookup(method, GET, fields);
		if (field == null) {
			return null;
		}
		// TODO check return type
		return null;
	}

	private static <T extends WithMetaClass> MetaField<? super T> getFieldIs(Method method, Map<String, MetaField<? super T>> fields) {
		final MetaField<? super T> field = lookup(method, IS, fields);
		if (field == null) {
			return null;
		}
		// TODO check return type
		return null;
	}
	
	private static <T extends WithMetaClass> MetaField<? super T> lookup(Method method, String prefix, Map<String, MetaField<? super T>> fields) {
		final String methodName = method.getName();
		if (methodName.length() > prefix.length() && methodName.startsWith(prefix)) {
			String withoutPrefix = methodName.substring(prefix.length());
			String name = withoutPrefix.substring(0, 1).toLowerCase() + withoutPrefix.substring(1);
			return fields.get(name);
		}
		return null;
	}

	InterfaceMetaBuilderFactory(MetaClass<T> metaClass) {
		super(metaClass);
	}
}
