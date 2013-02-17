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
 * Class with metaclass and a single property.
 * @author Andres Rodriguez
 */
public final class SimpleClass implements WithMetaClass {
	/** Descriptor for name property. */
	public static final StringMetaProperty<SimpleClass> SAMPLE = new StringMetaProperty<SimpleClass>("sample", true) {
		public String apply(SimpleClass input) {
			return input.getSample();
		}
	};

	public SimpleClass() {
	}

	public String getSample() {
		return "sample";
	}
}
