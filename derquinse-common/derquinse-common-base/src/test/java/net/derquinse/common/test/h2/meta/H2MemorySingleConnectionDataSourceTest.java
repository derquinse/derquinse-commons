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
package net.derquinse.common.test.h2.meta;

import java.sql.SQLException;

import net.derquinse.common.test.h2.H2MemorySingleConnectionDataSource;

import org.testng.annotations.Test;

/**
 * Tests for H2MemorySingleConnectionDataSource
 * @author Andres Rodriguez
 */
public class H2MemorySingleConnectionDataSourceTest {
	/**
	 * Open.
	 */
	@Test
	public void open() throws SQLException {
		new H2MemorySingleConnectionDataSource().getConnection();
	}
}
