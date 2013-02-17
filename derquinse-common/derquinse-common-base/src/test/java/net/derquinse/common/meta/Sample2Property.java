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
package net.derquinse.common.meta;

/**
 * Property name property.
 * @author Andres Rodriguez
 */
public interface Sample2Property extends WithMetaClass {
	/** Descriptor for name property. */
	StringMetaProperty<Sample2Property> SAMPLE2 = new StringMetaProperty<Sample2Property>("sample2", true) {
		public String apply(Sample2Property input) {
			return input.getSample2();
		}
	};

	String getSample2();
}
