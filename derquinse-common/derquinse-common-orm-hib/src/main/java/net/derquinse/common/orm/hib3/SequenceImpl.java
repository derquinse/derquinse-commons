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
package net.derquinse.common.orm.hib3;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.derquinse.common.orm.Sequence;

import com.google.common.base.Preconditions;

/**
 * Entity representing database row-backed sequences. The use of these kind of sequences is the
 * possibility of using them transactionally. WARNING: Transactional sequences are a source of locks
 * and may hurt scalability.
 * @author Andres Rodriguez
 */
@Entity
@Table(name = "DRQJ_SEQUENCE")
public class SequenceImpl implements Sequence {
	/** Name of the sequence (identifier). */
	@Id
	@Column(name = "TXSQ_NAME", nullable = false, unique = true, updatable = false)
	private String id;
	/** Current value of the sequence. */
	@Column(name = "TXSQ_CURRENT", nullable = false)
	private long current;
	/** Increment value of the sequence. */
	@Column(name = "TXSQ_INCREMENT", nullable = false)
	private long increment = 1;

	/** Default constructor. */
	public SequenceImpl() {
	}

	/**
	 * Initializes a new sequence.
	 * @param id Sequence name.
	 * @param initial Initial value.
	 * @param increment Increment between values.
	 */
	SequenceImpl(final String id, final long initial, final long increment) {
		if (increment == 0) {
			throw new IllegalArgumentException("Increment cannot be zero");
		}
		this.id = Preconditions.checkNotNull(id, "Sequence name is mandatory.");
		this.current = initial;
		this.increment = increment;
	}

	/**
	 * Returns the name of the sequence.
	 * @return The name of the sequence.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the current value of the sequence.
	 * @return The current value of the sequence.
	 */
	public long getCurrent() {
		return current;
	}

	/**
	 * Increments the sequence.
	 * @return The new value of the sequence.
	 */
	public long getNext() {
		current += increment;
		return current;
	}

	/**
	 * Returns the increment to use in the sequence.
	 * @return The increment to use in the sequence.
	 */
	public long getIncrement() {
		return increment;
	}

	/**
	 * Sets the new increment. It must be same sign and greater absolute value then the old one.
	 * @param increment The new increment.
	 */
	public void setIncrement(final long increment) {
		if (increment == 0) {
			throw new IllegalArgumentException("Increment cannot be zero");
		}
		if (this.increment == increment) {
			return;
		}
		if ((this.increment > 0 && this.increment > increment) || (this.increment < 0 && this.increment < increment)) {
			throw new IllegalArgumentException("New increment must be same sign and greater absolute value then the old one");
		}
		this.increment = increment;
	}
}
