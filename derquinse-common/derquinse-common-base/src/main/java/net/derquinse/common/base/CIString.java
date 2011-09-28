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
package net.derquinse.common.base;

import java.io.Serializable;

import javax.annotation.Nullable;

import com.google.common.base.Function;

/**
 * A string holder that implements equals ignoring case.
 * @author Andres Rodriguez
 */
public final class CIString implements Serializable, Comparable<CIString> {
	/** Serial UID. */
	private static final long serialVersionUID = 1050129007442556236L;
	/** Hash code. */
	private final int hash;
	/** Original string. */
	private final String string;

	private CIString(final String string) {
		this.string = string;
		this.hash = string.substring(0, Math.min(string.length(), 32)).toLowerCase().hashCode();
	}

	private static final Function<String, CIString> VALUEOF_FUNCTION = new Function<String, CIString>() {
		public CIString apply(String from) {
			return valueOf(from);
		};
	};

	/**
	 * Builds a new case ignoring string from a existing string.
	 * @param string Original string.
	 * @return A requested object or {@code null} if the provided string is {@code null}.
	 */
	public static CIString valueOf(@Nullable String string) {
		return string == null ? null : new CIString(string);
	}

	/**
	 * Returns the valueOf static method as a function.
	 */
	public static Function<String, CIString> valueOfFunction() {
		return VALUEOF_FUNCTION;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof CIString) {
			final CIString cis = (CIString) obj;
			return hash == cis.hash || string.equalsIgnoreCase(cis.string);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(CIString o) {
		return string.compareToIgnoreCase(o.string);
	}

	@Override
	public String toString() {
		return string;
	}

}
