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

import static com.codahale.metrics.MetricRegistry.name;
import static com.google.common.base.Preconditions.checkNotNull;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.google.common.cache.Cache;

/**
 * Metrics for a Guava {@link Cache}. The cache must record stats for the metrics to be useful.
 * @author Andres Rodriguez
 */
public final class CacheMetrics {
	/** Cache to instrument. */
	private final Cache<?, ?> cache;
	/** Average load penalty. */
	private final AverageLoadPenalty averageLoadPenalty;
	/** Eviction Count. */
	private final EvictionCount evictionCount;
	/** Hit Count. */
	private final HitCount hitCount;
	/** Hit rate. */
	private final HitRate hitRate;
	/** Load Count. */
	private final LoadCount loadCount;
	/** Load Exception Count. */
	private final LoadExceptionCount loadExceptionCount;
	/** Load Exception Rate. */
	private final LoadExceptionRate loadExceptionRate;
	/** Load Success Count. */
	private final LoadSuccessCount loadSuccessCount;
	/** Miss Count. */
	private final MissCount missCount;
	/** Miss rate. */
	private final MissRate missRate;
	/** Request Count. */
	private final RequestCount requestCount;
	/** Total load time. */
	private final TotalLoadTime totalLoadTime;

	/** Creates metrics for a {@link Cache} based on its statistics. */
	public static CacheMetrics of(Cache<?, ?> cache) {
		return new CacheMetrics(cache);
	}

	/** Constructor. */
	private CacheMetrics(Cache<?, ?> cache) {
		this.cache = checkNotNull(cache, "The cache to extract metrics from must be provided");
		this.averageLoadPenalty = new AverageLoadPenalty();
		this.evictionCount = new EvictionCount();
		this.hitCount = new HitCount();
		this.hitRate = new HitRate();
		this.loadCount = new LoadCount();
		this.loadExceptionCount = new LoadExceptionCount();
		this.loadExceptionRate = new LoadExceptionRate();
		this.loadSuccessCount = new LoadSuccessCount();
		this.missCount = new MissCount();
		this.missRate = new MissRate();
		this.requestCount = new RequestCount();
		this.totalLoadTime = new TotalLoadTime();
	}

	/** Registers the cache metrics in a registry. */
	public CacheMetrics register(MetricRegistry registry, String baseName) {
		registry.register(name(baseName, "averageLoadPenalty"), averageLoadPenalty);
		registry.register(name(baseName, "evictionCount"), evictionCount);
		registry.register(name(baseName, "hitCount"), hitCount);
		registry.register(name(baseName, "hitRate"), hitRate);
		registry.register(name(baseName, "loadCount"), loadCount);
		registry.register(name(baseName, "loadExceptionCount"), loadExceptionCount);
		registry.register(name(baseName, "loadExceptionRate"), loadExceptionRate);
		registry.register(name(baseName, "loadSuccessCount"), loadSuccessCount);
		registry.register(name(baseName, "missCount"), missCount);
		registry.register(name(baseName, "missRate"), missRate);
		registry.register(name(baseName, "requestCount"), requestCount);
		registry.register(name(baseName, "totalLoadTime"), totalLoadTime);
		return this;
	}

	/** Average load penalty. */
	public Gauge<Double> getAverageLoadPenalty() {
		return averageLoadPenalty;
	}

	/** Eviction Count. */
	public Gauge<Long> getEvictionCount() {
		return evictionCount;
	}

	/** Hit Count. */
	public Gauge<Long> getHitCount() {
		return hitCount;
	}

	/** Hit rate. */
	public Gauge<Double> getHitRate() {
		return hitRate;
	}

	/** Load Count. */
	public Gauge<Long> getLoadCount() {
		return loadCount;
	}

	/** Load Exception Count. */
	public Gauge<Long> getLoadExceptionCount() {
		return loadExceptionCount;
	}

	/** Load Exception Rate. */
	public Gauge<Double> getLoadExceptionRate() {
		return loadExceptionRate;
	}

	/** Load Success Count. */
	public Gauge<Long> getLoadSuccessCount() {
		return loadSuccessCount;
	}

	/** Miss Count. */
	public Gauge<Long> getMissCount() {
		return missCount;
	}

	/** Miss Rate. */
	public Gauge<Double> getMissRate() {
		return missRate;
	}

	/** Request Count. */
	public Gauge<Long> getRequestCount() {
		return requestCount;
	}

	/** Total load time. */
	public Gauge<Long> getTotalLoadTime() {
		return totalLoadTime;
	}

	/** Average load penalty. */
	private class AverageLoadPenalty implements Gauge<Double> {
		@Override
		public Double getValue() {
			return cache.stats().averageLoadPenalty();
		}
	}

	/** Eviction Count. */
	private class EvictionCount implements Gauge<Long> {
		@Override
		public Long getValue() {
			return cache.stats().evictionCount();
		}
	}

	/** Hit Count. */
	private class HitCount implements Gauge<Long> {
		@Override
		public Long getValue() {
			return cache.stats().hitCount();
		}
	}

	/** Hit rate. */
	private class HitRate implements Gauge<Double> {
		@Override
		public Double getValue() {
			return cache.stats().hitRate();
		}
	}

	/** Load Count. */
	private class LoadCount implements Gauge<Long> {
		@Override
		public Long getValue() {
			return cache.stats().loadCount();
		}
	}

	/** Load Exception Count. */
	private class LoadExceptionCount implements Gauge<Long> {
		@Override
		public Long getValue() {
			return cache.stats().loadExceptionCount();
		}
	}

	/** Load Exception Rate. */
	private class LoadExceptionRate implements Gauge<Double> {
		@Override
		public Double getValue() {
			return cache.stats().loadExceptionRate();
		}
	}

	/** Load Success Count. */
	private class LoadSuccessCount implements Gauge<Long> {
		@Override
		public Long getValue() {
			return cache.stats().loadSuccessCount();
		}
	}

	/** Miss Count. */
	private class MissCount implements Gauge<Long> {
		@Override
		public Long getValue() {
			return cache.stats().missCount();
		}
	}

	/** Miss rate. */
	private class MissRate implements Gauge<Double> {
		@Override
		public Double getValue() {
			return cache.stats().missRate();
		}
	}

	/** Request Count. */
	private class RequestCount implements Gauge<Long> {
		@Override
		public Long getValue() {
			return cache.stats().requestCount();
		}
	}

	/** Total load time. */
	private class TotalLoadTime implements Gauge<Long> {
		@Override
		public Long getValue() {
			return cache.stats().totalLoadTime();
		}
	}

}
