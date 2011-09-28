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
package net.derquinse.common.orm;

/**
 * Data access object interface for the sequence entity. As it represents a very specific case, it
 * does not follow normal best-practices (interface-based, etc.). WARNING: Transactional sequences
 * are a source of locks and may hurt scalability.
 * @author Andres Rodriguez
 */
public interface SequenceDAO {
	/**
	 * Gets the current value of a sequence.
	 * @param id Sequence name.
	 * @return The current value of the specified sequence.
	 * @throws SequenceNotFoundException if the sequence is not found.
	 */
	long getCurrentValue(final String id) throws SequenceNotFoundException;

	/**
	 * Gets the next value of a sequence. The row is locked in the current transaction.
	 * @param id Sequence name.
	 * @return The next value of the specified sequence.
	 * @throws SequenceNotFoundException if the sequence is not found.
	 */
	long getNextValue(final String id) throws SequenceNotFoundException;

	/**
	 * Gets the next value of a sequence, creating it if it does not exist. The row is locked in the
	 * current transaction.
	 * @param id Sequence name.
	 * @param initial Initial value.
	 * @param increment Increment between values.
	 * @return The next value of the specified sequence.
	 */
	long getNextValue(final String id, final long initial, final long increment);
}
