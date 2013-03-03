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

import static com.google.common.base.Preconditions.checkNotNull;
import net.derquinse.common.base.NotInstantiable;

import org.hibernate.cfg.Configuration;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.UserType;

/**
 * Hibernate configurations support class.
 * @author Andres Rodriguez
 */
public class Configurations extends NotInstantiable {
	/** Not instantiable. */
	private Configurations() {
	}

	/**
	 * Register Joda-Time (from usertype) and user types defined in this package in the provided
	 * configuration.
	 */
	public static Configuration registerTypes(Configuration configuration) {
		checkNotNull(configuration, "The configuration must be provided");
		registerTypes(configuration, new ByteStringUserType(), new HashCodeUserType(), new LocaleUserType());
		return configuration;
	}

	private static void registerTypes(Configuration configuration, UserType... types) {
		for (UserType type : types) {
			String name = type.returnedClass().getName();
			configuration.registerTypeOverride(type, new String[] { name });
		}
	}

	@SuppressWarnings("unused")
	private static void registerTypes(Configuration configuration, CompositeUserType... types) {
		for (CompositeUserType type : types) {
			String name = type.returnedClass().getName();
			configuration.registerTypeOverride(type, new String[] { name });
		}
	}
}
