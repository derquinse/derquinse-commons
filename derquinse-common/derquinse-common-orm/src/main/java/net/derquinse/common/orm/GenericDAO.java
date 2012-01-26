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
import java.util.List;
import java.util.Map;

/**
 * Generic DAO interface.
 * @author Andres Rodriguez
 * @param <T> Persistent type.
 * @param <ID> Primary Key type.
 */
public interface GenericDAO<T extends Entity<ID>, ID extends Serializable> extends DAO {
	/**
	 * Finds an entity by ID.
	 * @param id ID
	 * @param lock If the entity shall be locked.
	 * @return The requested entity or {@code null} if it is not found.
	 */
	T findById(ID id, boolean lock);

	/**
	 * Finds a set of entities by ID.
	 * @param ids IDs to find.
	 * @return The requested entity or {@code null} if it is not found.
	 */
	Map<ID, T> findByIds(Iterable<? extends ID> ids);

	/**
	 * Returns all entities of the persistent type.
	 * @return A list with all the instances.
	 */
	List<T> findAll();

	/**
	 * Saves an entity.
	 * @param entity Entity to save.
	 * @return The persisted entity.
	 */
	T save(T entity);

	/**
	 * Updates an entity.
	 * @param entity Entity to update.
	 * @return The persisted entity.
	 */
	T update(T entity);

	/**
	 * Saves or updates an entity, depending upon resolution of the unsaved-value checks.
	 * @param entity Entity to persist.
	 * @return The persisted entity.
	 */
	T saveOrUpdate(T entity);

	/**
	 * Deletes an entity instance.
	 * @param entity Instance to delete.
	 */
	void delete(T entity);

	/**
	 * Deletes an entity instance.
	 * @param id Id of the instance to delete.
	 */
	void deleteById(ID id);
}
