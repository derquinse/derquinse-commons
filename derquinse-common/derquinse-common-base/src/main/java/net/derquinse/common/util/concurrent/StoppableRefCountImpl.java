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
package net.derquinse.common.util.concurrent;

import static com.google.common.base.Preconditions.checkState;

import java.util.concurrent.atomic.AtomicReference;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * A thread-safe stoppable reference-counted object implementation..
 * @author Andres Rodriguez
 * @param <T> Supplied object type.
 */
final class StoppableRefCountImpl<T> extends AbstractRefCount<T> implements StoppableRefCount<T> {
	/** Stopped State. */
	private static final Stopped STOPPED = new Stopped();
	/** State. */
	private final AtomicReference<State> state = new AtomicReference<State>(new On(0));
	/** Future. */
	private final SimpleListenableFuture<Object> future = new SimpleListenableFuture<Object>();

	/**
	 * Constructor.
	 * @param object Supplied object.
	 */
	StoppableRefCountImpl(T object) {
		super(object);
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.concurrent.RefCount#getCount()
	 */
	public int getCount() {
		return state.get().getCount();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.concurrent.RefCount#dispose()
	 */
	public void dispose() {
		State current;
		State next;
		do {
			current = state.get();
			next = current.dispose();
		} while (!state.compareAndSet(current, next));
		if (next == STOPPED) {
			future.set(STOPPED);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.concurrent.AbstractRefCount#newRef()
	 */
	@Override
	protected int newRef() {
		State current;
		State next;
		do {
			current = state.get();
			next = current.newRef();
		} while (!state.compareAndSet(current, next));
		return next.getCount();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.concurrent.StoppableRefCount#isAvailable()
	 */
	public boolean isAvailable() {
		return state.get() instanceof On;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.concurrent.StoppableRefCount#isRunning()
	 */
	public boolean isRunning() {
		return state.get().isRunning();
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.util.concurrent.StoppableRefCount#stop()
	 */
	public ListenableFuture<?> stop() {
		State current;
		State next = null;
		do {
			current = state.get();
			if (current instanceof On) {
				next = ((On) current).stop();
			} else
				break;
		} while (!state.compareAndSet(current, next));
		if (next == STOPPED) {
			future.set(STOPPED);
		}
		return future;
	}

	private static abstract class State {
		abstract boolean isRunning();

		abstract int getCount();

		abstract State newRef();

		abstract State dispose();
	}

	private static final class On extends State {
		private final int count;

		On(int count) {
			this.count = count;
		}

		@Override
		boolean isRunning() {
			return true;
		}

		@Override
		int getCount() {
			return count;
		}

		@Override
		State newRef() {
			return new On(count + 1);
		}

		@Override
		State dispose() {
			checkState(count > 0);
			return new On(count - 1);
		}

		State stop() {
			return count > 0 ? new Stopping(count) : STOPPED;
		}
	}

	private static abstract class AbstractStopped extends State {
		AbstractStopped() {
		}

		@Override
		final State newRef() {
			throw new IllegalStateException();
		}
	}

	private static final class Stopping extends AbstractStopped {
		private final int count;

		Stopping(int count) {
			this.count = count;
		}

		@Override
		boolean isRunning() {
			return true;
		}

		@Override
		int getCount() {
			return count;
		}

		@Override
		State dispose() {
			checkState(count > 0);
			return count > 1 ? new Stopping(count - 1) : STOPPED;
		}
	}

	private static final class Stopped extends AbstractStopped {
		Stopped() {
		}

		@Override
		boolean isRunning() {
			return false;
		}

		@Override
		int getCount() {
			return 0;
		}

		@Override
		State dispose() {
			throw new IllegalStateException();
		}
	}
}
