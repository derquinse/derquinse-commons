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

import java.security.SecureRandom;
import java.util.UUID;

/**
 * Time based UUID generator.
 * @author Andres Rodriguez
 */
public final class TimeBasedUUIDGenerator implements UUIDGenerator {
	/** Max sequence number. */
	private static final int MAX_SEQ = 0x3FFF;
	/** Mask for the significant bits. */
	private static final long NODE_MASK = 0x0000FFFFFFFFFFFFL;
	/** Multicast bit. */
	private static final long MULTICAST = 0x0000100000000000L;
	/** Minimum number of bits of the node part. */
	private static final int MIN_NODE = 0;
	/** Maximum number of bits of the node part. */
	private static final int MAX_NODE = 47;

	/** Sequence number. */
	private int sequence;
	/** UUID timer. */
	private final UUIDTimer timer;
	/** Timer resolution. */
	private final long resolution;
	/** Last used time. */
	private long lastTime = 0L;
	/** Last used sub-time. */
	private long lastSubtime = 0L;
	/** Node number. */
	private final long node;
	/** Mask for the random part of the node. */
	private final long randomMask;
	/** Random number generator. */
	private final SecureRandom random;

	public TimeBasedUUIDGenerator(UUIDTimer timer, long node, int bits) {
		if (timer == null) {
			timer = SystemUUIDTimer.INSTANCE;
		}
		final SecureRandom sr = new SecureRandom();
		this.sequence = sr.nextInt(MAX_SEQ);
		this.timer = timer;
		this.resolution = timer.getResolution();
		bits = Math.max(MIN_NODE, bits);
		bits = Math.min(MAX_NODE, bits);
		if (bits == MAX_NODE) {
			this.random = null;
			this.node = ((node | MULTICAST) & NODE_MASK);
			this.randomMask = 0L;
		} else {
			this.random = sr;
			if (bits == 0) {
				this.node = MULTICAST;
				this.randomMask = NODE_MASK;
			} else {
				this.node = ((NODE_MASK >>> (48 - bits)) & node) | MULTICAST;
				this.randomMask = (NODE_MASK << bits) & NODE_MASK;
			}
		}
	}

	public TimeBasedUUIDGenerator(UUIDTimer timer) {
		this(timer, 0L, 0);
	}

	public TimeBasedUUIDGenerator(long node, int bits) {
		this(null, node, bits);
	}

	public TimeBasedUUIDGenerator() {
		this(null, 0L, 0);
	}

	private UUID build(final long time, final long node) {
		// Most significant long
		long hi = 0x0000000000001000L; // version
		hi |= (time << 32); // time_low
		hi |= ((time >>> 16) & 0x00000000FFFF0000L); // time_mid
		hi |= ((time >>> 48) & 0x0000000000000FFFL); // time_hi
		// Least significant long
		long lo = 0x8000000000000000L; // variant
		lo |= (((long) sequence) << 48); // clock_seq
		lo |= node; // node
		return new UUID(hi, lo);
	}

	public UUID get() {
		final long time = getTime(timer.getCurrentTime());
		final long randomNode;
		if (random == null) {
			randomNode = 0L;
		} else {
			randomNode = random.nextLong() & randomMask;
		}
		return build(time, node | randomNode);
	}

	/**
	 * The synchronized code to build the time part. The current time is passed as an argument so that
	 * no alien function is called from the synchronized block.
	 * @param current Current time.
	 * @return The time component.
	 */
	private synchronized long getTime(long current) {
		if (current > lastTime) {
			lastTime = current;
			lastSubtime = 0L;
		} else {
			lastSubtime++;
			if (lastSubtime < resolution) {
				lastTime++;
			} else {
				lastTime = current;
				lastSubtime = 0L;
				sequence = ((sequence + 1) & MAX_SEQ);
			}
		}
		return lastTime;
	}
}
