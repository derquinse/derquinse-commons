/*
 * Copyright 2009 the original author or authors.
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
package net.derquinse.common.property;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Abstract implementation for members.
 * @author Andres Rodriguez
 * @param <E> Enclosing type.
 */
public abstract class AbstractMember<E> implements Member<E> {
	/** Member name. */
	private final String name;
	/** Whether the member is immutable. */
	private final boolean immutable;

	/**
	 * Constructor.
	 * @param name Member name.
	 * @param immutable Whether the member is immutable.
	 * @param predicate Validity predicate.
	 */
	AbstractMember(String name, boolean immutable) {
		this.name = checkNotNull(name, "The member name must be provided.");
		this.immutable = immutable;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.Member#getName()
	 */
	@Override
	public final String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.Member#isImmutable()
	 */
	@Override
	public final boolean isImmutable() {
		return immutable;
	}
}
