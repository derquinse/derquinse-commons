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
package net.derquinse.common.base;

import com.google.common.base.Function;

/**
 * Interface for functions that count number of requests the receive.
 * @author Andres Rodriguez
 * @param <F> The type of the function input.
 * @param <T> The type of the function output.
 */
public interface CountingFunction<F, T> extends Function<F, T> {
	/**
	 * Returns the number of requests made.
	 * @return The number of requests.
	 */
	long getRequestedValues();
}