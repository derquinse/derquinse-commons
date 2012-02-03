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
package net.derquinse.common.orm;

import java.io.Serializable;

import net.derquinse.common.base.NotInstantiable;

import com.google.common.base.Objects;

/**
 * Support methods for entities.
 * @author Andres Rodriguez
 */
public final class Entities extends NotInstantiable {
	private Entities() {
	}

	/**
	 * Checks if an object is an instance of an entity type and its id is equals to the provided
	 * entity id.
	 * @param entity Entity.
	 * @param type Entity type.
	 * @param obj Object to check.
	 * @return The object casts to type if neither the object nor the entity are null, obj is an
	 *         instance of type and the object and the entity have the same id (even if the id is
	 *         null). Null otherwise.
	 */
	public static <ID extends Serializable, T extends Entity<ID>> T idEquals(Entity<ID> entity, Class<? extends T> type, Object obj) {
		if (entity == null || obj == null) {
			return null;
		}
		if (type.isInstance(obj)) {
			T other = type.cast(obj);
			if (Objects.equal(entity.getId(), other.getId())) {
				return other;
			}
		}
		return null;
	}

	/**
	 * Returns the hash code of the id of the entity. If either the entity or its id are null a constant number is returned.
	 */
	public static int idHashCode(Entity<?> entity) {
		if (entity != null) {
			Object id = entity.getId();
			if (id != null) {
				return id.hashCode();
			}
		}
		return 1046;
	}

}
