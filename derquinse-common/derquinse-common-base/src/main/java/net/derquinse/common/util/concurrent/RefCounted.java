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
package net.derquinse.common.util.concurrent;

import net.derquinse.common.base.Disposable;

import com.google.common.base.Supplier;

/**
 * Interface for suppliers of reference counted disposable objects.
 * @param <T> the type of the disposable object.
 * @author Andres Rodriguez
 */
public interface RefCounted<T> extends Supplier<Disposable<T>> {
	/**
	 * Returns a new the disposabe reference. The return value will be valid until it is disposed.
	 * @throws IllegalStateException if the object has been disposed.
	 */
	Disposable<T> get();
}
