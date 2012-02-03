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
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import net.derquinse.common.orm.AbstractEntity;
import net.derquinse.common.orm.LongEntity;

/**
 * Abstract superclass for model entities with an long primary key.
 * @author Andres Rodriguez
 */
@MappedSuperclass
public abstract class MappedLongEntity extends AbstractEntity<Long> implements LongEntity {
	/** Entity ID. */
	@Id
	@Column(name = "META_ID", nullable = false)
	private Long id;

	/** Default constructor. */
	public MappedLongEntity() {
	}

	/**
	 * Constructor.
	 * @param id Entity ID.
	 */
	public MappedLongEntity(Long id) {
		this.id = id;
	}

	/**
	 * Returns the entity id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the entity id.
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
