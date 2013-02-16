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
package net.derquinse.common.reflect;

import java.lang.reflect.Type;

import com.google.common.reflect.TypeToken;

/**
 * Skeletal implementation of a type descriptors.
 * @author Andres Rodriguez
 * @param <T> Described type.
 */
public class AbstractTypeDescriptor<T> implements TypeDescriptor<T> {
	/** Type token. */
	@SuppressWarnings("serial")
	private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) {
	};

	/** Constructor. */
	protected AbstractTypeDescriptor() {
	}

	/**
	 * Constructor with a raw type.
	 * @param type Raw type.
	 */
	protected AbstractTypeDescriptor(Class<T> type) {
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.reflect.WithTypeTokenProperty#getTypeToken()
	 */
	@Override
	public final TypeToken<T> getTypeToken() {
		return typeToken;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.reflect.WithTypeProperty#getType()
	 */
	@Override
	public final Type getType() {
		return typeToken.getType();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.reflect.AboutType#getRawType()
	 */
	@Override
	public Class<? super T> getRawType() {
		return typeToken.getRawType();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.reflect.AboutType#isRawType()
	 */
	@Override
	public final boolean isRawType() {
		return getType() == getRawType();
	}
}
