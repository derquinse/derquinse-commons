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

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Primitives;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeToken;

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
	/** Boolean type token. */
	private static final TypeToken<Boolean> BOOLEAN = TypeToken.of(Boolean.class);

	static <T extends WithMetaClass> InterfaceMetaBuilderFactory<T> of(MetaClass<T> metaClass) {
		Class<?> klass = metaClass.getType().getRawType();
		// 1 - It must be an interface.
		if (!klass.isInterface()) {
			return null;
		}
		// 2 - Method map builder
		ImmutableMap.Builder<String, MetaField<? super T, ?>> builder = ImmutableMap.builder();
		// 3 - Method loop
		for (Method m : klass.getMethods()) {
			if (m.getParameterTypes().length != 0) {
				// Method with arguments -> not buildable.
				return null;
			}
			MetaField<? super T, ?> field = getField(m, metaClass.getFields());
			if (field == null) {
				// The method is not a getter -> not buildable.
				return null;
			}
			builder.put(m.getName(), field);
		}
		return new InterfaceMetaBuilderFactory<T>(metaClass, builder.build());
	}

	private static <T extends WithMetaClass> MetaField<? super T, ?> getField(Method method, FieldMap<T> fields) {
		MetaField<? super T, ?> field = getFieldGet(method, fields);
		if (field != null) {
			return field;
		}
		return getFieldIs(method, fields);
	}

	private static <T extends WithMetaClass> MetaField<? super T, ?> getFieldGet(Method method,
			Map<String, MetaField<? super T, ?>> fields) {
		final MetaField<? super T, ?> field = lookup(method, GET, fields);
		if (field instanceof MetaProperty<?, ?>) {
			return checkReturnType(method, field, ((MetaProperty<?, ?>) field).getFieldType());
		}
		return null;
	}

	private static <T extends WithMetaClass> MetaField<? super T, ?> getFieldIs(Method method,
			Map<String, MetaField<? super T, ?>> fields) {
		final MetaField<? super T, ?> field = lookup(method, IS, fields);
		if (field instanceof MetaFlag<?>) {
			return checkReturnType(method, field, field.getFieldType());
		}
		if (field instanceof MetaProperty<?, ?>) {
			if (!BOOLEAN.isAssignableFrom(((MetaProperty<?, ?>) field).getFieldType())) {
				return null; // Invalid property type.
			}
		} else if (!(field instanceof MetaFlag<?>)) {
			return null; // should not happen
		}
		return checkReturnType(method, field, BOOLEAN);
	}

	private static <T extends WithMetaClass> MetaField<? super T, ?> lookup(Method method, String prefix,
			Map<String, MetaField<? super T, ?>> fields) {
		final String methodName = method.getName();
		if (methodName.length() > prefix.length() && methodName.startsWith(prefix)) {
			String withoutPrefix = methodName.substring(prefix.length());
			String name = withoutPrefix.substring(0, 1).toLowerCase() + withoutPrefix.substring(1);
			return fields.get(name);
		}
		return null;
	}

	private static <T extends WithMetaClass> MetaField<? super T, ?> checkReturnType(Method method,
			MetaField<? super T, ?> field, TypeToken<?> fieldType) {
		TypeToken<?> returnType = Invokable.from(method).getReturnType();
		// Check for primitive types
		if (Primitives.allPrimitiveTypes().contains(returnType.getRawType())) {
			returnType = TypeToken.of(Primitives.wrap(returnType.getRawType()));
		}
		if (returnType.isAssignableFrom(fieldType)) {
			return field;
		}
		return null;
	}

	/** Method map. */
	@SuppressWarnings("unused")
	private final ImmutableMap<String, MetaField<? super T, ?>> methodMap;

	private InterfaceMetaBuilderFactory(MetaClass<T> metaClass, ImmutableMap<String, MetaField<? super T, ?>> methodMap) {
		super(metaClass);
		this.methodMap = checkNotNull(methodMap);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.meta.MetaBuilderFactory#newBuilder()
	 */
	@Override
	MetaBuilder<T> newBuilder() {
		return new InterfaceMetaBuilder();
	}

	private class InterfaceMetaBuilder extends MetaBuilder<T> {
		/** Constructor. */
		InterfaceMetaBuilder() {
			super(InterfaceMetaBuilderFactory.this.getMetaClass());
		}

		/*
		 * (non-Javadoc)
		 * @see net.derquinse.common.base.Builder#build()
		 */
		@Override
		public T build() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
