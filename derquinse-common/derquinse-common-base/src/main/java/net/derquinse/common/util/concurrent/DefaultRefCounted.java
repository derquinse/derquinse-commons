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

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

import net.derquinse.common.base.Disposable;
import net.derquinse.common.base.IntegerWaterMark;

import com.google.common.util.concurrent.Atomics;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * A supplier of reference counted disposable objects.
 * @param <T> the type of the disposable object.
 * @author Andres Rodriguez
 */
final class DefaultRefCounted<T> implements RefCounted<T> {
	/** State. */
	private final AtomicReference<State<T>> state;
	/** Shutdown task. */
	private final FutureTask<Long> task;
	/** Executor. */
	private volatile Executor executor;

	DefaultRefCounted(T value, Runnable hook) {
		this.state = Atomics.newReference(new State<T>(checkNotNull(value, "The referenced value is mandatory")));
		this.task = new FutureTask<Long>(new Hook(hook));
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.concurrent.RefCounted#get()
	 */
	public Disposable<T> get() {
		while (true) {
			final State<T> current = state.get();
			final State<T> next = current.inc();
			if (state.compareAndSet(current, next)) {
				return new RefDisposable(next.get());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.concurrent.RefCounted#getCount()
	 */
	@Override
	public int getCount() {
		return state.get().count();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.concurrent.RefCounted#getMaxCount()
	 */
	@Override
	public int getMaxCount() {
		return state.get().max();
	}

	/**
	 * Decrements the reference count.
	 */
	private void dec() {
		while (true) {
			final State<T> current = state.get();
			final State<T> next = current.dec();
			if (state.compareAndSet(current, next)) {
				runHook();
				return;
			}
		}
	}

	/*
	 * @see net.derquinse.common.util.concurrent.RefCounted#shutdown(java.util.concurrent.Executor)
	 */
	@Override
	public Future<Long> shutdown(Executor executor) {
		checkNotNull(executor);
		while (!tryShutdown(executor));
		return task;
	}

	public boolean tryShutdown(Executor executor) {
		final State<T> current = state.get();
		if (current.closed()) {
			return true;
		}
		this.executor = executor;
		final State<T> next = current.close();
		if (state.compareAndSet(current, next)) {
			runHook();
			return true;
		}
		return false;
	}
	
	/*
	 * @see net.derquinse.common.util.concurrent.RefCounted#shutdown()
	 */
	@Override
	public Future<Long> shutdown() {
		return shutdown(MoreExecutors.sameThreadExecutor());
	}

	private void runHook() {
		State<T> s = state.get();
		if (s.closed() && s.count() == 0) {
			executor.execute(task);
		}
	}

	private final class RefDisposable implements Disposable<T> {
		private final AtomicReference<T> ref;

		RefDisposable(T value) {
			this.ref = Atomics.newReference(value);
		}

		@Override
		public T get() {
			T value = ref.get();
			checkState(value != null, "Reference already disposed");
			return value;
		}

		@Override
		public void dispose() {
			if (ref.get() != null) {
				ref.set(null);
				dec();
			}
		}
	}

	/**
	 * Reference state.
	 */
	private static class State<T> {
		/** Reference count. */
		private final IntegerWaterMark count;
		/** Value to provide (null if closed). */
		private final T value;

		State(T value) {
			count = IntegerWaterMark.of();
			this.value = checkNotNull(value, "The referenced value is mandatory");
		}

		State(IntegerWaterMark count, T value) {
			this.count = count;
			this.value = value;
		}

		private void checkOpen() {
			checkState(value != null, "Already closed");
		}

		State<T> close() {
			checkOpen();
			return new State<T>(count, null);
		}

		boolean closed() {
			return value == null;
		}

		T get() {
			checkOpen();
			return value;
		}

		int count() {
			return count.get();
		}

		int max() {
			return count.getMax();
		}

		State<T> inc() {
			checkOpen();
			return new State<T>(count.inc(), value);
		}

		State<T> dec() {
			int current = count.get();
			checkState(current > 0, "No reference to remove");
			return new State<T>(count.dec(), value);
		}
	}

	/**
	 * Shutdown hook.
	 */
	private static final class Hook implements Callable<Long> {
		/** Shutdown hook. */
		private final Runnable hook;

		Hook(Runnable hook) {
			this.hook = checkNotNull(hook, "The shutdown hook must be provided");
		}

		@Override
		public Long call() throws Exception {
			hook.run();
			return System.currentTimeMillis();
		}
	}

}
