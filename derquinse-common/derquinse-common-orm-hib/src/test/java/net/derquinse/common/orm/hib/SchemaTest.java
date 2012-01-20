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

import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.testng.annotations.Test;

/**
 * Tests used to print DB schemas.
 * @author Andres Rodriguez
 */
public class SchemaTest {
	private void showSchema(String name, Configuration configuration, Dialect dialect) {
		// Generate schema
		String prefix = "\n\n***** " + name + " - " + dialect.getClass().getSimpleName() + " ***: ";
		System.out.println(prefix + "START");
		String[] script = configuration.generateSchemaCreationScript(dialect);
		for (String s : script) {
			System.out.println(s);
		}
		System.out.println(prefix + "END");
	}

	@Test
	public void basic() {
		showSchema("Basic", TestConfigurations.basic(), new MySQL5InnoDBDialect());
		showSchema("Basic", TestConfigurations.basic(), new H2Dialect());
		showSchema("Basic", TestConfigurations.basic(), new Oracle10gDialect());
	}

	@Test
	public void types() {
		showSchema("Types", TestConfigurations.types(), new MySQL5InnoDBDialect());
		showSchema("Types", TestConfigurations.types(), new H2Dialect());
		showSchema("Types", TestConfigurations.types(), new Oracle10gDialect());
	}
}
