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
package net.derquinse.common.reflect;

import com.google.common.base.Function;

/**
 * Interface for the "raw type" property.
 * @author Andres Rodriguez
 */
public interface WithRawTypeProperty {
	Function<WithRawTypeProperty, Class<?>> RAW_TYPE = new Function<WithRawTypeProperty, Class<?>>() {
		public Class<?> apply(WithRawTypeProperty from) {
			return from.getRawType();
		}
	};

	/**
	 * Returns the raw type.
	 * @return The raw type.
	 */
	Class<?> getRawType();
}
