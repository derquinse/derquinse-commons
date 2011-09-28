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
 * Interface for entities representing database row-backed sequences. The use of these kind of
 * sequences is the possibility of using them transactionally. WARNING: Transactional sequences are
 * a source of locks and may hurt scalability.
 * @author Andres Rodriguez
 */
public interface Sequence {
	/**
	 * Returns the name of the sequence.
	 * @return The name of the sequence.
	 */
	String getId();

	/**
	 * Returns the current value of the sequence.
	 * @return The current value of the sequence.
	 */
	long getCurrent();

	/**
	 * Returns the increment to use in the sequence.
	 * @return The increment to use in the sequence.
	 */
	long getIncrement();

	/**
	 * Sets the new increment. It must be same sign and greater absolute value then the old one.
	 * @param increment The new increment.
	 * @throws IllegalArgumentException if the new increment is not valid.
	 */
	void setIncrement(final long increment);

	/**
	 * Increments the sequence.
	 * @return The new value of the sequence.
	 */
	long getNext();

}
