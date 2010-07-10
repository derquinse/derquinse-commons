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
package net.derquinse.common.util.cache;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;

/**
 * Caching functions helper object.
 * @author Andres Rodriguez
 */
public final class CachingFunctions {
	/** Not instantiable. */
	private CachingFunctions() {
	}

	/**
	 * Returns a function cached by a concurrent map.
	 * @param maker Map maker to use.
	 * @param function Function to cache.
	 * @return The caching function.
	 */
	public static <F, T> CachingFunction<F, T> byMap(MapMaker maker, Function<? super F, ? extends T> function) {
		return new MapCachingFunction<F, T>(maker, function);
	}
}
