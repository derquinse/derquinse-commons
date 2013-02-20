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

import java.util.concurrent.ConcurrentMap;

import net.derquinse.common.log.ContextLog;

import com.google.common.collect.MapMaker;
import com.google.common.reflect.Reflection;
import com.google.common.reflect.TypeToken;

/**
 * Meta class map.
 * @author Andres Rodriguez
 */
final class MetaClassMap {
	/** Field map. */
	private final ConcurrentMap<TypeToken<?>, MetaClass<?>> map = new MapMaker().makeMap();
	/** Logger. */
	private final ContextLog log = ContextLog.of("meta-classes");

	/**
	 * Default constructor.
	 */
	MetaClassMap() {
	}

	/** Checks if the map contains an entry for a type token. */
	boolean contains(TypeToken<?> type) {
		return map.containsKey(checkNotNull(type, "The type must be provided"));
	}

	/** Returns an entry from the map or {@code null} if the entry does not exist. */
	<T extends WithMetaClass> MetaClass<T> get(TypeToken<T> type) {
		MetaClass<T> metaClass = internalGet(checkNotNull(type, "The type must be provided"));
		if (metaClass == null) {
			// Try to initialize the class
			Reflection.initialize(type.getRawType());
			metaClass = internalGet(type);
		}
		return metaClass;
	}

	/** Returns an entry from the map or {@code null} if the entry does not exist. */
	@SuppressWarnings("unchecked")
	private <T extends WithMetaClass> MetaClass<T> internalGet(TypeToken<T> type) {
		// Trusted.
		return (MetaClass<T>) map.get(checkNotNull(type, "The type must be provided"));
	}

	/**
	 * Puts an entry in the map if there was no previous one. If there was a previous entry a warning
	 * is logged, no modification and an exception is thrown.
	 * @throws DuplicateMetaClassException if there was a previous entry.
	 */
	@SuppressWarnings("unchecked")
	<T extends WithMetaClass> void put(MetaClass<T> metaClass) {
		// Trusted.
		MetaClass<T> previous = (MetaClass<T>) map.putIfAbsent(metaClass.getType(), metaClass);
		if (previous != null) {
			log.warn("Duplicate registration for type [%s]", metaClass.getType());
			throw new DuplicateMetaClassException(previous);
		}
	}
}
