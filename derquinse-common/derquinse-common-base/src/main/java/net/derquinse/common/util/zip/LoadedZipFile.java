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
package net.derquinse.common.util.zip;

import static net.derquinse.common.util.zip.InternalPreconditions.checkLoader;

import java.io.IOException;
import java.util.Map;

import net.derquinse.common.io.MemoryByteSource;
import net.derquinse.common.io.MemoryByteSourceLoader;

import com.google.common.base.Function;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.UncheckedExecutionException;

/**
 * Zip file loaded into memory.
 * @author Andres Rodriguez
 */
public final class LoadedZipFile extends ForwardingMap<String, MemoryByteSource> {
	/** Loader used. */
	private final MemoryByteSourceLoader loader;
	/** Contents. */
	private final ImmutableMap<String, MemoryByteSource> entryMap;

	/** Constructor. */
	LoadedZipFile(MemoryByteSourceLoader loader, Map<String, ? extends MemoryByteSource> entries) {
		this.loader = checkLoader(loader);
		this.entryMap = ImmutableMap.copyOf(entries);
	}

	@Override
	protected Map<String, MemoryByteSource> delegate() {
		return entryMap;
	}

	/**
	 * Transform the entry map.
	 */
	private <T> Map<String, T> transform(Function<? super MemoryByteSource, T> f) throws IOException {
		try {
			return ImmutableMap.copyOf(Maps.transformValues(entryMap, f));
		} catch (UncheckedExecutionException e) {
			Throwable t = e.getCause();
			if (t instanceof IOException) {
				throw (IOException) t;
			}
			throw e;
		}
	}

	/**
	 * Compress the entries with GZIP.
	 */
	public Map<String, MemoryByteSource> gzip() throws IOException {
		return transform(GZIP.gzip(loader));
	}

	/**
	 * Compress the entries with GZIP. Those smaller than the original will be kept in compressed
	 * form.
	 */
	public Map<String, MaybeCompressed<MemoryByteSource>> maybeGzip() throws IOException {
		return transform(GZIP.maybeGzip(loader));
	}
}
