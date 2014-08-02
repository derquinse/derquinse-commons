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

import net.derquinse.common.base.ByteString;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import com.google.common.base.Objects;
import com.google.common.hash.HashCode;

/**
 * An hibernate user type representing a {@link HashCode} as a byte array.
 * @author Andres Rodriguez
 */
public class HashCodeUserType implements UserType {
	/** SQL Types. */
	private static final int[] TYPES = { Types.BINARY };

	/** Get the names under which this type should be registered in the type registry. */
	public static String[] getRegistrationKeys() {
		return new String[] { HashCodeUserType.class.getName() };
	}

	/** Default constructor. */
	public HashCodeUserType() {
	}

	@Override
	public int[] sqlTypes() {
		return TYPES;
	}

	@Override
	public Class<?> returnedClass() {
		return HashCode.class;
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
		final byte[] bytes = rs.getBytes(names[0]);
		return (bytes == null || bytes.length == 0) ? null : HashCode.fromBytes(bytes);
	}

	/*
	 * (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object,
	 * int)
	 */
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		final byte[] bytes = (value == null) ? null : ((HashCode) value).asBytes();
		st.setBytes(index, bytes);
	}

	/* HashCodes are immutable. */
	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	/* ByteStrings are serializable and immutable, so they are used as cached image. */
	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		if (value == null) {
			return null;
		}
		return ByteString.copyFrom((HashCode) value);
	}

	/* ByteStrings are serializable and immutable, so they are used as cached image. */
	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		if (cached == null) {
			return null;
		}
		return ((ByteString) cached).toHashCode();
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}
}
