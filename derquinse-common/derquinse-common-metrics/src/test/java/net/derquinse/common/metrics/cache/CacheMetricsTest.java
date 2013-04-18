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

import java.util.Map.Entry;

import org.testng.annotations.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.yammer.metrics.Gauge;
import com.yammer.metrics.MetricRegistry;

/**
 * Tests for CacheMetrics.
 * @author Andres Rodriguez
 */
public class CacheMetricsTest {
	/** Register metrics. */
	@Test
	@SuppressWarnings("rawtypes")
	public void register() {
		LoadingCache<Integer, Integer> cache = CacheBuilder.newBuilder().recordStats()
				.build(new CacheLoader<Integer, Integer>() {
					@Override
					public Integer load(Integer key) throws Exception {
						return key + 1;
					}
				});
		for (int i = 0; i < 100; i++) {
			cache.getUnchecked(i);
		}
		for (int i = 50; i < 150; i++) {
			cache.getUnchecked(i);
		}
		MetricRegistry metrics = new MetricRegistry("test");
		CacheMetrics.of(cache).register(metrics, "cache");
		for (Entry<String, Gauge> entry : metrics.getGauges().entrySet()) {
			System.out.printf("%s - %s\n", entry.getKey(), entry.getValue().getValue());
		}
	}
}
