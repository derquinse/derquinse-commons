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

/**
 * Base class for meta builder factories.
 * @author Andres Rodriguez
 * @param <T> Built type.
 */
abstract class MetaBuilderFactory<T extends WithMetaClass> {
	/** Meta class. */
	private final MetaClass<T> metaClass;

	MetaBuilderFactory(MetaClass<T> metaClass) {
		this.metaClass = checkNotNull(metaClass);
	}

	/** Returns the metaclass of the object to build. */
	final MetaClass<T> getMetaClass() {
		return metaClass;
	}
}
