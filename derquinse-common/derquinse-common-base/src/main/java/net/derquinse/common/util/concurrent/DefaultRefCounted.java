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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import net.derquinse.common.base.Disposable;

/**
 * A supplier of reference counted disposable objects.
 * @param <T> the type of the disposable object.
 * @author Andres Rodriguez
 */
final class DefaultRefCounted<T> implements RefCounted<T> {
	/** Value to provide. */
	private final T value;

	DefaultRefCounted(T value) {
		this.value = checkNotNull(value, "The referenced value is mandatory");
	}

	public Disposable<T> get() {
		return new RefDisposable();
	}

	private final class RefDisposable implements Disposable<T> {
		private boolean disposed = false;

		RefDisposable() {
		}

		@Override
		public synchronized T get() {
			checkState(!disposed, "Reference already disposed");
			return value;
		}

		@Override
		public void dispose() {
			synchronized (this) {
				if (disposed) {
					return;
				}
				disposed = true;
			}
			// TODO: notify parent object.
		}
	}

}
