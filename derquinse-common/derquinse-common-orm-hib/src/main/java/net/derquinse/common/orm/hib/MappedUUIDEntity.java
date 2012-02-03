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

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import net.derquinse.common.orm.AbstractEntity;
import net.derquinse.common.orm.UUIDEntity;

/**
 * Abstract superclass for model entities with an UUID primary key, stored in binary form.
 * @author Andres Rodriguez
 */
@MappedSuperclass
public abstract class MappedUUIDEntity extends AbstractEntity<UUID> implements UUIDEntity {
	/** Entity ID. */
	@Id
	@Column(name = "META_ID", length = HibLengths.UUID_BINARY, nullable = false)
	private UUID id;

	/** Default constructor. */
	public MappedUUIDEntity() {
	}

	/**
	 * Constructor.
	 * @param id Entity ID.
	 */
	public MappedUUIDEntity(UUID id) {
		this.id = id;
	}

	/**
	 * Returns the entity id.
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Sets the entity id.
	 */
	public void setId(UUID id) {
		this.id = id;
	}
}
