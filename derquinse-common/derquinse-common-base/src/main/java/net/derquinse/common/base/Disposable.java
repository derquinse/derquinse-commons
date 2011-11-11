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
package net.derquinse.common.base;

import com.google.common.base.Supplier;

/**
 * Interface to provide objects that must be disposed when no longer in use.
 * @param <T> the type of the disposable object.
 * @author Andres Rodriguez
 */
public interface Disposable<T> extends Supplier<T> {
	/**
	 * Returns the disposabe object. The return value is always the same object.
	 * @throws IllegalStateException if the object has been disposed.
	 */
	T get();
	
	/**
	 * Called when the object is no longer in use. It can be called safely multiple times.
	 * Once called, every call to get will throw {@link IllegalStateException}. 
	 */
	void dispose();
}
