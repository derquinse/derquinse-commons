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
package net.derquinse.common.metrics.cache;

import net.derquinse.common.base.NotInstantiable;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.yammer.metrics.core.MetricsRegistry;

/**
 * Class that registers metrics for a Guava {@link Cache}.
 * @author Andres Rodriguez
 */
public final class CacheMetricsBuilder {
	/** Cache to instrument. */
	private final Cache<?, ?> cache;
	/** Class to instrument. */
	private Class<?> klass = Cache.class;
	/** Group name. */
	private String group = null;
	/** Type name. */
	private String type = null;
	
	/** Constructor. */
	private CacheMetricsBuilder(Cache<?, ?> cache) {
		this.cache = Preconditions.checkNotNull(cache, "The cache to extract metrics from must be provided");
	}

	//public static MetricsRegistry of()
}
