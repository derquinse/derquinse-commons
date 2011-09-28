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
import java.util.List;

import net.derquinse.common.orm.GeneralDAO;

import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;

/**
 * Implementation of the General DAO.
 * @author Andres Rodriguez
 */
public class HibernateGeneralDAO extends AbstractHibernateDAO implements GeneralDAO {
	/**
	 * Constructs the DAO
	 * @param sessionFactory Hibernate Session factory.
	 */
	public HibernateGeneralDAO(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/**
	 * Constructs the DAO
	 */
	public HibernateGeneralDAO() {
	}

	/**
	 * Returns a new criteria object.
	 * @param type Entity type.
	 * @return A new criteria object.
	 */
	protected final Criteria newCriteria(Class<?> type) {
		return getSession().createCriteria(type);
	}

	/**
	 * Returns the list of entity instances that matches the provided crtieria.
	 * @param type Entity type.
	 * @param criterion Search criteria.
	 * @return The results.
	 */
	protected final <T> List<T> findByCriteria(Class<T> type, Criterion... criterion) {
		final Criteria criteria = newCriteria(type);
		for (Criterion c : criterion) {
			criteria.add(c);
		}
		@SuppressWarnings("unchecked")
		final List<T> list = criteria.list();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.orm.GeneralDAO#delete(java.lang.Object)
	 */
	public void delete(Object entity) {
		getSession().delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.orm.GeneralDAO#deleteById(java.lang.Class, java.io.Serializable)
	 */
	public <T> void deleteById(Class<T> type, Serializable id) {
		Object entity = findById(type, id, true);
		if (entity != null) {
			delete(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.orm.GeneralDAO#findAll(java.lang.Class)
	 */
	public <T> List<T> findAll(Class<T> type) {
		return findByCriteria(type);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.orm.GeneralDAO#findById(java.lang.Class, java.io.Serializable,
	 * boolean)
	 */
	@SuppressWarnings("unchecked")
	public <T> T findById(Class<T> type, Serializable id, boolean lock) {
		if (lock) {
			return (T) getSession().get(type, id, LockOptions.UPGRADE);
		}
		return (T) getSession().get(type, id);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.orm.GeneralDAO#save(java.lang.Object)
	 */
	public void save(Object entity) {
		getSession().save(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.orm.GeneralDAO#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(Object entity) {
		getSession().saveOrUpdate(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.orm.GeneralDAO#update(java.lang.Object)
	 */
	public void update(Object entity) {
		getSession().update(entity);
	}

}
