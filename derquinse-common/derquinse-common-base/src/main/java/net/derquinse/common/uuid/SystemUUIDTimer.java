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
package net.derquinse.common.uuid;

/**
 * Simple UUID timer based on System.currentTimeMillis.
 * @author Andres Rodriguez
 */
public final class SystemUUIDTimer implements UUIDTimer {
	/** Multiplier from system time resolution to UUID time resolution. */
	private static final long MULTIPLIER = 10000L;
	/** Offset between system time and UUID time zeroes. */
	private static final long OFFSET = 0x01b21dd213814000L;

	public static final SystemUUIDTimer INSTANCE = new SystemUUIDTimer();

	public SystemUUIDTimer() {
	}

	public long getCurrentTime() {
		return System.currentTimeMillis() * MULTIPLIER + OFFSET;
	}

	public long getResolution() {
		return MULTIPLIER;
	}
}
