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
package net.derquinse.common.reflect;

import com.google.common.base.Predicate;

/**
 * Base interface for type descriptors.
 * @author Andres Rodriguez
 * @param <T> Described type.
 */
public interface AboutType<T> extends WithTypeProperty, WithRawTypeProperty {
	/**
	 * Returns the raw type.
	 * @return The raw type.
	 */
	Class<? super T> getRawType();

	/** Is raw type predicate. */
	Predicate<AboutType<?>> IS_RAW_TYPE = new Predicate<AboutType<?>>() {
		public boolean apply(AboutType<?> input) {
			return input.isRawType();
		}
	};

	/**
	 * Returns whether the described type is a raw type.
	 * @return True if the described type is a raw type.
	 */
	boolean isRawType();
}
