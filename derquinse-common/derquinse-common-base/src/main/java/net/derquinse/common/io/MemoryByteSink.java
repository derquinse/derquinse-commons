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
package net.derquinse.common.io;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.google.common.annotations.Beta;
import com.google.common.collect.Queues;
import com.google.common.io.ByteSink;
import com.google.common.util.concurrent.ForwardingBlockingQueue;

/**
 * Memory byte sink. Every time an atomic write operation is performed on the sink or an stream
 * opened through the sink is closed, a MemoryByteSource is added to the queue.
 * @author Andres Rodriguez
 */
@Beta
public final class MemoryByteSink extends ByteSink {
	/** Loader to use. */
	private final MemoryByteSourceLoader loader;
	/** Queue. */
	private final LinkedBlockingQueue<MemoryByteSource> queue = Queues.newLinkedBlockingQueue();
	/** Queue view. */
	private final BlockingQueue<MemoryByteSource> view = new Queue();

	/** Constructor. */
	MemoryByteSink(MemoryByteSourceLoader loader) {
		this.loader = checkNotNull(loader);
	}

	/**
	 * Adds a loaded byte source to the sink queue. The source is merged if specified by the loader.
	 */
	MemoryByteSource add(MemoryByteSource source) {
		checkNotNull(source);
		if (loader.isMerge()) {
			source = source.merge();
		}
		queue.add(source);
		return source;
	}

	/**
	 * Returns a view of the sink queue. All operations that add elements to the queue throw
	 * {@link UnsupportedOperationException}.
	 */
	public BlockingQueue<MemoryByteSource> queue() {
		return view;
	}

	@Override
	public OutputStream openStream() throws IOException {
		if (loader.isDirect()) {
			return DirectByteSource.openStream(this, loader.getMaxSize(), loader.getChunkSize());
		}
		return HeapByteSource.openStream(this, loader.getMaxSize(), loader.getChunkSize());
	}

	private final class Queue extends ForwardingBlockingQueue<MemoryByteSource> {
		/** Constructor. */
		Queue() {
		}

		@Override
		protected BlockingQueue<MemoryByteSource> delegate() {
			return queue;
		}

		@Override
		public boolean add(MemoryByteSource e) {
			throw new UnsupportedOperationException("Can't add elements to the sink queue");
		}

		@Override
		public boolean addAll(Collection<? extends MemoryByteSource> c) {
			throw new UnsupportedOperationException("Can't add elements to the sink queue");
		}

		@Override
		public boolean offer(MemoryByteSource e) {
			throw new UnsupportedOperationException("Can't add elements to the sink queue");
		}

		@Override
		public boolean offer(MemoryByteSource e, long timeout, TimeUnit unit) throws InterruptedException {
			throw new UnsupportedOperationException("Can't add elements to the sink queue");
		}

		@Override
		public void put(MemoryByteSource e) throws InterruptedException {
			throw new UnsupportedOperationException("Can't add elements to the sink queue");
		}

	}

}
