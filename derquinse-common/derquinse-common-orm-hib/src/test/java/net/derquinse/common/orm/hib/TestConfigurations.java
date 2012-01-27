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

import net.derquinse.common.test.h2.H2Tests;

import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.H2Dialect;

/**
 * Hibernate configurations used in tests.
 * @author Andres Rodriguez
 */
class TestConfigurations {
	/** Not instantiable. */
	private TestConfigurations() {
	}

	/** Creates a basic configuration. */
	static Configuration basic() {
		return new Configuration().addAnnotatedClass(TestUUIDEntity.class);
	}

	/** Creates a configuration with types. */
	static Configuration types() {
		return Configurations.registerTypes(basic());
	}

	/** Decorates a configuration with H2 database information. */
	static Configuration h2(Configuration c) {
		c.setProperty("hibernate.connection.driver_class", H2Tests.DRIVER);
		c.setProperty("hibernate.connection.url", H2Tests.MEM_URL);
		c.setProperty("hibernate.connection.username", H2Tests.MEM_USER);
		c.setProperty("hibernate.connection.password", H2Tests.MEM_PWD);
		c.setProperty("hibernate.connection.pool_size", "1");
		c.setProperty("hibernate.hbm2ddl.auto", "update");
		c.setProperty("hibernate.dialect", H2Dialect.class.getName());
		return c;
	}

}
