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
package net.derquinse.common.orm.hib3;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import net.derquinse.common.orm.GenericDAO;

import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;

import com.google.common.base.Preconditions;

/**
 * Implementation of the Generic DAO.
 * @author Andres Rodriguez
 */
public abstract class HibernateGenericDAO<T, ID extends Serializable> extends AbstractHibernateDAO implements
		GenericDAO<T, ID> {
	/** Persistent class. */
	private Class<T> persistentClass;

	/**
	 * Constructs the DAO
	 * @param sessionFactory Hibernate Session factory.
	 */
	public HibernateGenericDAO(final SessionFactory sessionFactory) {
		super(sessionFactory);
		@SuppressWarnings("unchecked")
		final Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.persistentClass = type;
	}

	/**
	 * Constructs the DAO.
	 * @param persistentClass Persistent class.
	 * @param sessionFactory Hibernate Session factory.
	 */
	public HibernateGenericDAO(final Class<T> persistentClass, final SessionFactory sessionFactory) {
		super(sessionFactory);
		this.persistentClass = checkNotNull(persistentClass);
	}

	/**
	 * Constructs the DAO
	 */
	public HibernateGenericDAO() {
		@SuppressWarnings("unchecked")
		final Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.persistentClass = type;
	}

	/**
	 * Constructs the DAO.
	 * @param persistentClass Persistent class.
	 */
	public HibernateGenericDAO(final Class<T> persistentClass) {
		this.persistentClass = checkNotNull(persistentClass);
	}

	/**
	 * Returns the persistent class.
	 * @return The persistent class.
	 */
	protected final Class<T> getPersistentClass() {
		return persistentClass;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.orm.GenericDAO#delete(java.lang.Object)
	 */
	public void delete(T entity) {
		Preconditions.checkNotNull(entity);
		getSession().delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.orm.GenericDAO#deleteById(java.io.Serializable)
	 */
	public void deleteById(ID id) {
		Preconditions.checkNotNull(id);
		T entity = findById(id, true);
		if (entity != null) {
			delete(entity);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.orm.GenericDAO#findAll()
	 */
	public List<T> findAll() {
		return findByCriteria();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.orm.GenericDAO#findById(java.io.Serializable, boolean)
	 */
	@SuppressWarnings("unchecked")
	public T findById(ID id, boolean lock) {
		if (lock) {
			return (T) getSession().get(persistentClass, id, LockOptions.UPGRADE);
		}
		return (T) getSession().get(persistentClass, id);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.orm.GenericDAO#save(java.lang.Object)
	 */
	public T save(T entity) {
		Preconditions.checkNotNull(entity);
		getSession().save(entity);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.orm.GenericDAO#saveOrUpdate(java.lang.Object)
	 */
	public T saveOrUpdate(T entity) {
		Preconditions.checkNotNull(entity);
		getSession().saveOrUpdate(entity);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.orm.GenericDAO#update(java.lang.Object)
	 */
	public T update(T entity) {
		Preconditions.checkNotNull(entity);
		getSession().update(entity);
		return entity;
	}

	/**
	 * Returns a new criteria object.
	 * @return A new criteria object.
	 */
	protected final Criteria newCriteria() {
		return getSession().createCriteria(persistentClass);
	}

	/**
	 * Returns the list of entity instances that matches the provided crtieria.
	 * @param criterion Search criteria.
	 * @return The results.
	 */
	protected final List<T> findByCriteria(Criterion... criterion) {
		final Criteria criteria = newCriteria();
		for (Criterion c : criterion) {
			criteria.add(c);
		}
		@SuppressWarnings("unchecked")
		final List<T> list = criteria.list();
		return list;
	}

	/**
	 * Returns the results of a query as a list of the persisted class.
	 * @param query Query to execute.
	 * @return The results.
	 */
	protected final List<T> list(Query query) {
		return list(persistentClass, query);
	}

	/**
	 * Returns the first result of a query returning instances of the persisted class.
	 * @param query Query to execute.
	 * @return The first result or {@code null} if there are no results.
	 */
	protected final T first(Query query) {
		return first(persistentClass, query);
	}

	/**
	 * Returns the unique result of a query returning instances of the persisted class.
	 * @param query Query to execute.
	 * @return The unique result or {@code null} if there are no results.
	 * @throws NonUniqueResultException if there are more than one result.
	 */
	protected final T unique(Query query) {
		return unique(persistentClass, query);
	}

}
