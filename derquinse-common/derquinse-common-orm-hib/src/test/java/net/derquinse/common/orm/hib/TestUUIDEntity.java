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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.derquinse.common.base.ByteString;
import net.derquinse.common.base.Digests;

import org.joda.time.DateTime;

/**
 * Test UUID entity.
 * @author Andres Rodriguez
 */
@Entity
@Table(name = "TEST_UUID")
public class TestUUIDEntity extends MappedUUIDVersionedEntity {
	/** Date time. */
	@Column(name = "TEST_DATETIME", nullable = false)
	DateTime dateTime;

	/** SHA-1. */
	@Column(name = "TEST_SHA1", nullable = true, length = Digests.SHA1_LENGTH_BYTES)
	ByteString sha1;
}
