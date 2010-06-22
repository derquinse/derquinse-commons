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
 * A real function whose domain may not be the whole real numbers set.
 * @author Andres Rodriguez
 */
public interface PartialRealFunction extends RealFunction {
	/**
	 * Returns whether an input value is part of the function domain.
	 * @param input Input value.
	 * @return True if the input value is part of the function domain.
	 */
	boolean isDefinedAt(double input);
}
