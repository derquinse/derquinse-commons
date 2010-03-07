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
package net.derquinse.common.product;

/**
 * The interface Product3 defines a cartesian of 3 elements.
 * @author Andres Rodriguez
 * @param <T0> First element type.
 * @param <T1> Second element type.
 * @param <T2> Third element type.
 */
public interface Product3<T0, T1, T2> extends Product2<T0, T1> {
	/**
	 * Returns the third element.
	 * @return The third element.
	 */
	T2 get2();
}
