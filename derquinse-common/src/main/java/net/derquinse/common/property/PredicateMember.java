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

import com.google.common.base.Predicate;

/**
 * Predicate member implementation.
 * @author Andres Rodriguez
 * @param <E> Enclosing type.
 */
public abstract class PredicateMember<E> extends AbstractMember<E> implements Predicate<E> {
	/**
	 * Constructor.
	 * @param name Member name.
	 * @param immutable Whether the member is immutable.
	 */
	protected PredicateMember(String name, boolean immutable) {
		super(name, immutable);
	}

	/**
	 * Applies this predicate to the given object.
	 * @param from Enclosing object.
	 * @return The property value.
	 * @throws NullPointerException if the argument is {@code null}
	 */
	public abstract boolean apply(E from);
	
	/**
	 * Returns a property view of this member.
	 * @return A boolean property representing this member.
	 */
	public final BooleanProperty<E> asBooleanProperty() {
		return new BooleanProperty<E>(getName(), isImmutable(), false) {
			public Boolean apply(E from) {
				return PredicateMember.this.apply(from);
			};
		};
	}
}
