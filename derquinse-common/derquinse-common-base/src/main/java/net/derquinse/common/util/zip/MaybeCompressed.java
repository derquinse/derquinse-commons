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

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import net.derquinse.common.meta.MetaFlag;

import com.google.common.base.Objects;

/**
 * Decorator flag for an object that may be compressed.
 * @author Andres Rodriguez
 */
public final class MaybeCompressed<T> implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = -2115273664635743622L;

	/** Compressed flag. */
	public static final MetaFlag<MaybeCompressed<?>> COMPRESSED = new MetaFlag<MaybeCompressed<?>>("compressed") {
		public boolean apply(MaybeCompressed<?> input) {
			return input.isCompressed();
		}
	};

	/** Factory method. */
	public static <T> MaybeCompressed<T> of(boolean compressed, T payload) {
		return new MaybeCompressed<T>(compressed, payload);
	}

	/** Whether the object is compressed. */
	private final boolean compressed;
	/** Payload. */
	private final T payload;

	/** Constructor. */
	private MaybeCompressed(boolean compressed, T payload) {
		this.compressed = compressed;
		this.payload = checkNotNull(payload, "The payload must be provided");
	}

	/** Returns whether the payload is compressed. */
	public boolean isCompressed() {
		return compressed;
	}

	/** Returns the payload. */
	public T getPayload() {
		return payload;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(compressed, payload);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MaybeCompressed) {
			MaybeCompressed<?> other = (MaybeCompressed<?>) obj;
			return compressed == other.compressed && payload.equals(other.payload);
		}
		return false;
	}

	// =================================================================
	// Serialization proxy

	private static class SerializationProxy<T> implements Serializable {
		/** Serial UID. */
		private static final long serialVersionUID = 4049913691759555221L;
		/** Whether the object is compressed. */
		private final boolean compressed;
		/** Payload. */
		private final T payload;

		public SerializationProxy(MaybeCompressed<T> m) {
			this.compressed = m.compressed;
			this.payload = m.payload;
		}

		private Object readResolve() {
			return of(compressed, payload);
		}
	}

	private Object writeReplace() {
		return new SerializationProxy<T>(this);
	}

	private void readObject(ObjectInputStream stream) throws InvalidObjectException {
		throw new InvalidObjectException("Proxy required");
	}

}
