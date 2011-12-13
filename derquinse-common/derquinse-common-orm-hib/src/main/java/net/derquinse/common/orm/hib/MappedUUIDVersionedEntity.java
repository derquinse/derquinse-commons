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
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import net.derquinse.common.orm.VersionedEntity;

/**
 * Abstract superclass for versioned model entities with an UUID primary key, stored in binary form.
 * @author Andres Rodriguez
 */
@MappedSuperclass
public abstract class MappedUUIDVersionedEntity extends MappedUUIDEntity implements VersionedEntity<UUID> {
	/** Version. */
	@Version
	@Column(name = "META_VERSION")
	private Integer version;

	/** Default constructor. */
	public MappedUUIDVersionedEntity() {
	}

	/**
	 * Constructor.
	 * @param id Entity ID.
	 */
	public MappedUUIDVersionedEntity(UUID id) {
		super(id);
	}

	/** Returns the entity version. */
	@Override
	public Integer getVersion() {
		return version;
	}

}
