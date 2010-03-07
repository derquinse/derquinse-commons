/*
 * Copyright 2008-2010 the original author or authors.
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

/**
 * Abstract implementation for property members.
 * @author Andres Rodriguez
 * @param <E> Enclosing type.
 */
public abstract class AbstractPropertyMember<E> extends AbstractMember<E> implements PropertyMember<E> {
	/** Whether the property is immutable. */
	private final boolean immutable;

	/**
	 * Constructor.
	 * @param name Property name.
	 * @param immutable Whether the property is immutable.
	 */
	AbstractPropertyMember(String name, boolean immutable) {
		super(name);
		this.immutable = immutable;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.property.Property#isImmutable()
	 */
	public final boolean isImmutable() {
		return immutable;
	}
}
