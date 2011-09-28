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
package net.derquinse.common.orm.hib3;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.derquinse.common.orm.DAO;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Abstract implementation for general and generic DAO.
 * @author Andres Rodriguez
 */
public abstract class AbstractHibernateDAO implements DAO {
	private static SessionFactory check(SessionFactory sessionFactory) {
		return checkNotNull(sessionFactory, "A session factory must be provided.");
	}

	private static Query check(Query query) {
		return checkNotNull(query, "Null query provided");
	}

	/** Hibernate Session factory. */
	private SessionFactory sessionFactory;

	/**
	 * Constructs the DAO
	 * @param sessionFactory Hibernate Session factory.
	 */
	public AbstractHibernateDAO(final SessionFactory sessionFactory) {
		this.sessionFactory = check(sessionFactory);
	}

	/**
	 * Constructs the DAO
	 */
	public AbstractHibernateDAO() {
	}

	/**
	 * Sets the session factory to use.
	 * @param sessionFactory Hibernate session factory.
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Returns the current session.
	 * @return The current session.
	 */
	protected final Session getSession() {
		checkState(sessionFactory != null, "A session factory must have been provided");
		return sessionFactory.getCurrentSession();
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.hib3.DAO#clear()
	 */
	public void clear() {
		getSession().clear();
	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.hib3.DAO#flush()
	 */
	public void flush() {
		getSession().flush();

	}

	/*
	 * (non-Javadoc)
	 * @see net.sf.derquinsej.hib3.DAO#sync()
	 */
	public void sync() {
		final Session s = getSession();
		s.flush();
		s.clear();
	}

	/**
	 * Remove the provided instance from the session cache.
	 * @param object The persistent instance to remove.
	 */
	protected void evict(Object object) {
		getSession().evict(object);
	}

	/**
	 * Returns a externally defined named query.
	 * @param queryName Query name.
	 * @return The requested query.
	 */
	protected Query getNamedQuery(String queryName) {
		checkNotNull(queryName, "Null query name");
		return getSession().getNamedQuery(queryName);
	}

	/**
	 * Returns the results of a query as a list of a specified class.
	 * @param type Entity type.
	 * @param query Query to execute.
	 * @return The results.
	 */
	protected <T> List<T> list(Class<T> type, Query query) {
		@SuppressWarnings("unchecked")
		final List<T> list = check(query).list();
		return list;
	}

	/**
	 * Returns whether a query has results. Set the first result to 0 and the maximum number of
	 * results to 1.
	 * @param query Query to execute.
	 * @return True if the query provides any result, false otherwise.
	 */
	protected boolean hasRows(Query query) {
		final List<?> list = check(query).setFirstResult(0).setMaxResults(1).list();
		return list != null && !list.isEmpty();
	}

	/**
	 * Returns the first result of a query returning instances of a specified class.
	 * @param type Entity type.
	 * @param query Query to execute.
	 * @return The first result or {@code null} if there are no results.
	 */
	protected <T> T first(Class<T> type, Query query) {
		final List<T> list = list(type, query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * Returns the unique result of a query returning instances of a specified class.
	 * @param type Entity type.
	 * @param query Query to execute.
	 * @return The unique result or {@code null} if there are no results.
	 * @throws NonUniqueResultException if there are more than one result.
	 */
	protected <T> T unique(Class<T> type, Query query) {
		@SuppressWarnings("unchecked")
		final T result = (T) check(query).uniqueResult();
		return result;
	}

	/**
	 * Sets the parameters of a query to a list of non-null values.
	 * @param queryName Query name.
	 * @param parameters The parameters to supply.
	 * @return The provided query for method chaining.
	 */
	protected Query setParameters(Query query, Iterable<?> parameters) {
		check(query);
		checkNotNull(parameters, "Null parameters sequence");
		int i = 0;
		for (Object value : parameters) {
			query.setParameter(i, checkNotNull(value, "Null %d parameter", i));
			i++;
		}
		return query;
	}

	/**
	 * Sets the parameters of a query to a list of non-null values.
	 * @param queryName Query name.
	 * @param parameters The parameters to set.
	 * @return The provided query for method chaining.
	 */
	protected Query setParameters(Query query, Object... parameters) {
		return setParameters(query, Arrays.asList(parameters));
	}

	/**
	 * Sets named parameters of a query.
	 * @param queryName Query name.
	 * @param parameters The parameters to set.
	 * @return The provided query for method chaining.
	 */
	protected Query setParameters(Query query, Map<String, ?> parameters) {
		check(query);
		checkNotNull(parameters, "Null parameters map");
		for (Entry<String, ?> entry : parameters.entrySet()) {
			final String name = checkNotNull(entry.getKey(), "Null parameter name");
			query.setParameter(name, checkNotNull(entry.getValue(), "Null %s parameter value", name));
		}
		return query;
	}

	/**
	 * Returns a externally defined named query, with a list of non-null parameter values.
	 * @param queryName Query name.
	 * @param parameters The parameters to set.
	 * @return The requested query.
	 */
	protected Query getNamedQuery(String queryName, Iterable<?> parameters) {
		return setParameters(getNamedQuery(queryName), parameters);
	}

	/**
	 * Returns a externally defined named query, setting named parameters.
	 * @param queryName Query name.
	 * @param parameters The parameters to set.
	 * @return The requested query.
	 */
	protected Query getNamedQuery(String queryName, Map<String, ?> parameters) {
		return setParameters(getNamedQuery(queryName), parameters);
	}

	/**
	 * Returns a externally defined named query, with a list of non-null parameter values.
	 * @param queryName Query name.
	 * @param parameters The parameters to set.
	 * @return The requested query.
	 */
	protected Query getNamedQuery(String queryName, Object... parameters) {
		return setParameters(getNamedQuery(queryName), parameters);
	}

	/**
	 * Returns the results of a named query with a list of non-null parameter values as a list of a
	 * specified class.
	 * @param type Entity type.
	 * @param queryName Query name.
	 * @param parameters The parameters to set.
	 * @return The results.
	 */
	protected <T> List<T> list(Class<T> type, String queryName, Iterable<?> parameters) {
		return list(type, getNamedQuery(queryName, parameters));
	}

	/**
	 * Returns the results of a named query with a list of non-null parameter values as a list of a
	 * specified class.
	 * @param type Entity type.
	 * @param queryName Query name.
	 * @param parameters The parameters to set.
	 * @return The results.
	 */
	protected <T> List<T> list(Class<T> type, String queryName, Object... parameters) {
		return list(type, getNamedQuery(queryName, parameters));
	}

	/**
	 * Returns the results of a named query setting named parameters as a list of a specified class.
	 * @param type Entity type.
	 * @param queryName Query name.
	 * @param parameters The parameters to set.
	 * @return The results.
	 */
	protected <T> List<T> list(Class<T> type, String queryName, Map<String, ?> parameters) {
		return list(type, getNamedQuery(queryName, parameters));
	}

	/**
	 * Returns the unique result of a named query with a list of non-null parameter values returning
	 * instances of a specified class.
	 * @param type Entity type.
	 * @param queryName Query name.
	 * @param parameters The parameters to set.
	 * @return The unique result or {@code null} if there are no results.
	 * @throws NonUniqueResultException if there are more than one result.
	 */
	protected <T> T unique(Class<T> type, String queryName, Iterable<?> parameters) {
		return unique(type, getNamedQuery(queryName, parameters));
	}

	/**
	 * Returns the unique result of a named query with a list of non-null parameter values returning
	 * instances of a specified class.
	 * @param type Entity type.
	 * @param queryName Query name.
	 * @param parameters The parameters to set.
	 * @return The unique result or {@code null} if there are no results.
	 * @throws NonUniqueResultException if there are more than one result.
	 */
	protected <T> T unique(Class<T> type, String queryName, Object... parameters) {
		return unique(type, getNamedQuery(queryName, parameters));
	}

	/**
	 * Returns the unique result of a named query setting named parameters returning instances of a
	 * specified class.
	 * @param type Entity type.
	 * @param queryName Query name.
	 * @param parameters The parameters to set.
	 * @return The unique result or {@code null} if there are no results.
	 * @throws NonUniqueResultException if there are more than one result.
	 */
	protected <T> T unique(Class<T> type, String queryName, Map<String, ?> parameters) {
		return unique(type, getNamedQuery(queryName, parameters));
	}

}
