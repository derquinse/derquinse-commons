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
package net.derquinse.common.orm;

import java.io.Serializable;
import java.util.List;

/**
 * General DAO interface.
 * @author Andres Rodriguez
 */
public interface GeneralDAO extends DAO {
	/**
	 * Finds an entity by ID.
	 * @param type Entity type.
	 * @param id ID.
	 * @param lock If the entity shall be locked.
	 * @return The requested entity or {@code null} if it is not found.
	 */
	<T> T findById(Class<T> type, Serializable id, boolean lock);

	/**
	 * Returns all entities of the persistent type.
	 * @param type Entity type.
	 * @return A list with all the instances.
	 */
	<T> List<T> findAll(Class<T> type);

	/**
	 * Saves an entity.
	 * @param entity Entity to save.
	 */
	void save(Object entity);

	/**
	 * Updates an entity.
	 * @param entity Entity to update.
	 */
	void update(Object entity);

	/**
	 * Saves or updates an entity, depending upon resolution of the unsaved-value checks.
	 * @param entity Entity to persist.
	 * @return The persisted entity.
	 */
	void saveOrUpdate(Object entity);

	/**
	 * Deletes an entity instance.
	 * @param entity Instance to delete.
	 */
	void delete(Object entity);

	/**
	 * Deletes an entity instance.
	 * @param type Entity type.
	 * @param id Id of the instance to delete.
	 */
	<T> void deleteById(Class<T> type, Serializable id);
}
