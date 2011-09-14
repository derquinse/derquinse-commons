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
package net.derquinse.common.uuid;

/**
 * Simple interface for a timer for an UUID generator. Implementations MUST be thread-safe. The
 * timestamp is measured in in 100-nanosecond units since midnight, October 15, 1582 UTC. Only 60
 * bits are significant.
 * @author Andres Rodriguez
 */
public interface UUIDTimer {
	/**
	 * Returns the current time in 100-nanosecond units since midnight, October 15, 1582 UTC.
	 */
	long getCurrentTime();

	/**
	 * Returns the resolution of the current timer. This value is supposed to be constant for each
	 * timer, so it may be cached by clients.
	 */
	long getResolution();
}
