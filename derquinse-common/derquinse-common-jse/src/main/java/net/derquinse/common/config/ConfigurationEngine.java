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
package net.derquinse.common.config;

/**
 * Interface for a configuration engine.
 * @author Andres Rodriguez
 * @param <T> Base configuration interface.
 */
public interface ConfigurationEngine<T> {
	/**
	 * Returns the configuration descriptor for the provided configuration interface.
	 * @param type Configuration interface.
	 * @return The configuration descriptor.
	 * @throws NullPointerException if the argument is {@code null}.
	 * @throws IllegalArgumentException if the argument is not a valid configuration interface.
	 */
	<C extends T> ConfigurationDescriptor<C> get(Class<C> type);
}
