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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import net.derquinse.common.base.Disposable;
import net.derquinse.common.base.IntegerWaterMark;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.Atomics;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * A supplier of reference counted disposable objects.
 * @param <T> the type of the disposable object.
 * @author Andres Rodriguez
 */
final class DefaultRefCounted<T> implements RefCounted<T> {
	/** Referenced value. */
	private final T value;
	/** State. */
	private final AtomicReference<State> state;
	/** Shutdown task. */
	private final FutureTask<Long> task;
	/** Executor. */
	private volatile Executor executor;

	DefaultRefCounted(T value, Runnable hook) {
		this.value = checkNotNull(value, "The referenced value is mandatory");
		this.state = Atomics.newReference(new State());
		this.task = new FutureTask<Long>(new Hook(hook));
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.concurrent.RefCounted#get()
	 */
	public Disposable<T> get() {
		while (true) {
			final State current = state.get();
			final State next = current.inc();
			if (state.compareAndSet(current, next)) {
				return new RefDisposable(value);
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
			final State current = state.get();
			final State next = current.dec();
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
		while (!tryShutdown(executor))
			;
		return task;
	}

	public boolean tryShutdown(Executor executor) {
		final State current = state.get();
		if (current.closed()) {
			return true;
		}
		this.executor = executor;
		final State next = current.close();
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
		State s = state.get();
		if (s.closed() && s.count() == 0) {
			executor.execute(task);
		}
	}

	@Override
	public String toString() {
		State s = state.get();
		return Objects.toStringHelper(this).add("value", value).add("active", !s.closed()).add("count", s.count())
				.add("max", s.max()).toString();
	}

	private final class RefDisposable implements Disposable<T> {
		private final AtomicBoolean disposed = new AtomicBoolean(false);

		RefDisposable(T value) {
		}

		@Override
		public T get() {
			checkState(!disposed.get(), "Reference already disposed");
			return value;
		}

		@Override
		public void dispose() {
			while (!disposed.get()) {
				if (disposed.compareAndSet(false, true)) {
					dec();
				}
			}
		}

		@Override
		public String toString() {
			return String.format("%s{%s}", disposed.get() ? "Disposed" : "Disposable", value);
		}
	}

	/**
	 * Reference state.
	 */
	private static class State {
		/** Reference count. */
		private final IntegerWaterMark count;
		/** Whether is a closed state. */
		private final boolean closed;

		State() {
			this.count = IntegerWaterMark.of();
			this.closed = false;
		}

		State(IntegerWaterMark count, boolean closed) {
			this.count = count;
			this.closed = closed;
		}

		void checkOpen() {
			checkState(!closed, "Already closed");
		}

		State close() {
			checkOpen();
			return new State(count, true);
		}

		boolean closed() {
			return closed;
		}

		int count() {
			return count.get();
		}

		int max() {
			return count.getMax();
		}

		State inc() {
			checkOpen();
			return new State(count.inc(), false);
		}

		State dec() {
			int current = count.get();
			checkState(current > 0, "No reference to remove");
			return new State(count.dec(), closed);
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
