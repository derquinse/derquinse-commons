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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Locale;

import net.derquinse.common.i18n.Locales;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import com.google.common.base.Objects;

/**
 * An hibernate user type representing a Locale as string.
 * @author Andres Rodriguez
 */
public class LocaleUserType implements UserType {
	/** SQL Types. */
	private static final int[] TYPES = { Types.VARCHAR };

	/** Get the names under which this type should be registered in the type registry. */
	public static String[] getRegistrationKeys() {
		return new String[] { LocaleUserType.class.getName() };
	}

	/** Default constructor. */
	public LocaleUserType() {
	}

	@Override
	public int[] sqlTypes() {
		return TYPES;
	}

	@Override
	public Class<?> returnedClass() {
		return Locale.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return Objects.equal(x, y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x == null ? 0 : x.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[],
	 * java.lang.Object)
	 */
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		final String s = rs.getString(names[0]);
		return Locales.safeFromString(s);
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object,
	 * int)
	 */
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		final String s = (value == null) ? null : ((Locale) value).toString();
		st.setString(index, s);
	}

	/* Locales are immutable. */
	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	/* Locales are serializable and immutable, so they are their own cached image. */
	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	/* Locales are serializable and immutable, so they are their own cached image. */
	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}
}
