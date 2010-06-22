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
package net.derquinse.common.math;

/**
 * A function with a real domain and range.
 * @author Andres Rodriguez
 */
public interface RealFunction {
	/**
	 * Applies the function.
	 * @param input Input value.
	 * @return The output value.
	 * @throws IllegalArgumentException if the input value is not part of the domain.
	 */
	double apply(double input);
}
