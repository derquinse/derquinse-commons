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
package net.derquinse.common.orm.hib;

import java.io.Serializable;

import net.derquinse.common.orm.Entity;
import net.derquinse.common.orm.EntityDAO;

import org.hibernate.SessionFactory;

/**
 * Implementation of the Generic DAO for entities.
 * @author Andres Rodriguez
 */
public abstract class HibernateEntityDAO<T extends Entity<ID>, ID extends Serializable> extends
		HibernateGenericDAO<T, ID> implements EntityDAO<T, ID> {

	/**
	 * Constructs the DAO
	 * @param sessionFactory Hibernate Session factory.
	 */
	public HibernateEntityDAO(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/**
	 * Constructs the DAO.
	 * @param persistentClass Persistent class.
	 * @param sessionFactory Hibernate Session factory.
	 */
	public HibernateEntityDAO(final Class<T> persistentClass, final SessionFactory sessionFactory) {
		super(persistentClass, sessionFactory);
	}

	/**
	 * Constructs the DAO
	 */
	public HibernateEntityDAO() {
	}

	/**
	 * Constructs the DAO.
	 * @param persistentClass Persistent class.
	 */
	public HibernateEntityDAO(final Class<T> persistentClass) {
		super(persistentClass);
	}

}
