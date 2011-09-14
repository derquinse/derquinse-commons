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

import org.hibernate.SessionFactory;

/**
 * Data access object for the sequence entity. As it represents a very specific case, it does not
 * follow normal best-practices (interface-based, etc.). WARNING: Transactional sequences are a
 * source of locks and may hurt scalability.
 * @author Andres Rodriguez
 */
public class HibernateSequenceDAO extends AbstractHibernateSequenceDAO<SequenceImpl> {
	/**
	 * Initialized the DAO
	 * @param sessionFactory Hibernate Session factory.
	 */
	public HibernateSequenceDAO(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.orm.hib3.AbstractHibernateSequenceDAO#create(java.lang.String, long,
	 * long)
	 */
	@Override
	protected SequenceImpl create(String id, long initial, long increment) {
		return new SequenceImpl(id, initial, increment);
	}
}
