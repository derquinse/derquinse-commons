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

import net.derquinse.common.property.MetaProperty;

/**
 * Interface for the version property.
 * @author Andres Rodriguez
 */
public interface WithVersionProperty {
	/** Version property getter. */
	MetaProperty<WithVersionProperty, Integer> VERSION = new MetaProperty<WithVersionProperty, Integer>("version", true) {
		public Integer apply(WithVersionProperty from) {
			return from.getVersion();
		}
	};

	/**
	 * Returns the version.
	 * @return The value of the version property.
	 */
	int getVersion();
}
