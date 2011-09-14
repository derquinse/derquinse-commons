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

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import com.google.common.base.Objects;

/**
 * An hibernate user type to store java.util.UUID as Strings.
 * @author Andres Rodriguez
 */
public class UUIDUserType implements UserType {
	/** SQL Types. */
	private static final int[] TYPES = { Types.VARCHAR };

	/** Default constructor. */
	public UUIDUserType() {
	}

	/* UUIDs are serializable and immutable, so they are their own cached image. */
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	/* UUIDs are immutable. */
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	/* UUIDs are serializable and immutable, so they are their own cached image. */
	public Serializable disassemble(Object value) throws HibernateException {
		return (UUID) value;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return Objects.equal(x, y);
	}

	public int hashCode(Object x) throws HibernateException {
		return x == null ? 0 : x.hashCode();
	}

	public boolean isMutable() {
		return false;
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		final String uuid = rs.getString(names[0]);
		return uuid == null ? null : UUID.fromString(uuid);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		final String uuid = (value == null) ? null : value.toString().toLowerCase();
		st.setString(index, uuid);
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	public Class<?> returnedClass() {
		return UUID.class;
	}

	public int[] sqlTypes() {
		return TYPES;
	}
}
