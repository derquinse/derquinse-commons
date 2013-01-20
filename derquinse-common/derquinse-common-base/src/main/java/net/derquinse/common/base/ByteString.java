/*
 * Copyright (C) the original author or authors.
 * Copyright 2008 Google Inc.  All rights reserved.
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
package net.derquinse.common.base;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.charset.Charset;
import java.util.List;

import com.google.common.annotations.Beta;
import com.google.common.base.Charsets;

/**
 * Immutable array of bytes. Based on Google's code in Protocol Buffers. A version of this class is
 * expected to appear in Guava. This implementation will be removed once that happens.
 * @author crazybob@google.com Bob Lee
 * @author kenton@google.com Kenton Varda
 * @author Andres Rodriguez
 */
@Beta
public final class ByteString implements Serializable {
	/** Serial UID. */
	private static final long serialVersionUID = 5380545035055097521L;

	/** Used to build output as Hex. */
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	/** Used to build output as Hex. */
	private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	private final byte[] bytes;

	private ByteString(final byte[] bytes) {
		this.bytes = bytes;
	}

	/**
	 * Gets the byte at the given index.
	 * @throws ArrayIndexOutOfBoundsException {@code index} is < 0 or >= size
	 */
	public byte byteAt(final int index) {
		return bytes[index];
	}

	/**
	 * Gets the number of bytes.
	 */
	public int size() {
		return bytes.length;
	}

	/**
	 * Returns {@code true} if the size is {@code 0}, {@code false} otherwise.
	 */
	public boolean isEmpty() {
		return bytes.length == 0;
	}

	// =================================================================
	// byte[] -> ByteString

	/**
	 * Empty ByteString.
	 */
	public static final ByteString EMPTY = new ByteString(new byte[0]);

	/**
	 * Copies the given bytes into a {@code ByteString}.
	 */
	public static ByteString copyFrom(final byte[] bytes, final int offset, final int size) {
		final byte[] copy = new byte[size];
		System.arraycopy(bytes, offset, copy, 0, size);
		return new ByteString(copy);
	}

	/**
	 * Copies the given bytes into a {@code ByteString}.
	 */
	public static ByteString copyFrom(final byte[] bytes) {
		return copyFrom(bytes, 0, bytes.length);
	}

	/**
	 * Copies {@code size} bytes from a {@code java.nio.ByteBuffer} into a {@code ByteString}.
	 */
	public static ByteString copyFrom(final ByteBuffer bytes, final int size) {
		final byte[] copy = new byte[size];
		bytes.get(copy);
		return new ByteString(copy);
	}

	/**
	 * Copies the remaining bytes from a {@code java.nio.ByteBuffer} into a {@code ByteString}.
	 */
	public static ByteString copyFrom(final ByteBuffer bytes) {
		return copyFrom(bytes, bytes.remaining());
	}

	/**
	 * Encodes {@code text} into a sequence of bytes using the named charset and returns the result as
	 * a {@code ByteString}.
	 */
	public static ByteString copyFrom(final String text, final String charsetName) throws UnsupportedEncodingException {
		return new ByteString(text.getBytes(charsetName));
	}

	/**
	 * Encodes {@code text} into a sequence of bytes using the provided charset and returns the result
	 * as a {@code ByteString}.
	 */
	public static ByteString copyFrom(final String text, final Charset charset) throws UnsupportedEncodingException {
		return new ByteString(text.getBytes(charset.name()));
	}

	/**
	 * Encodes {@code text} into a sequence of UTF-8 bytes and returns the result as a
	 * {@code ByteString}.
	 */
	public static ByteString copyFromUtf8(final String text) {
		try {
			return copyFrom(text, Charsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 not supported?", e);
		}
	}

	/**
	 * Concatenates all byte strings in the list and returns the result.
	 * <p>
	 * The returned {@code ByteString} is not necessarily a unique object. If the list is empty, the
	 * returned object is the singleton empty {@code ByteString}. If the list has only one element,
	 * that {@code ByteString} will be returned without copying.
	 */
	public static ByteString copyFrom(List<ByteString> list) {
		if (list.size() == 0) {
			return EMPTY;
		} else if (list.size() == 1) {
			return list.get(0);
		}

		int size = 0;
		for (ByteString str : list) {
			size += str.size();
		}
		byte[] bytes = new byte[size];
		int pos = 0;
		for (ByteString str : list) {
			System.arraycopy(str.bytes, 0, bytes, pos, str.size());
			pos += str.size();
		}
		return new ByteString(bytes);
	}

	/**
	 * Converts an array of characters representing hexadecimal values into an array of bytes of those
	 * same values. The returned byte string will be half the length of the passed array, as it takes
	 * two characters to represent any given byte. An exception is thrown if the passed char array has
	 * an odd number of elements.
	 * @param data An array of characters containing hexadecimal digits
	 * @return A byte string containing binary data decoded from the supplied char array.
	 * @throws IllegalArgumentException Thrown if an odd number or illegal of characters is supplied
	 */
	public static ByteString fromHexChars(char[] data) {
		int len = data.length;
		checkArgument((len & 0x01) == 0, "Odd number of characters.");
		byte[] out = new byte[len >> 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}
		return new ByteString(out);
	}

	/**
	 * Converts an string representing hexadecimal values into an array of bytes of those same values.
	 * The returned byte string will be half the length of the passed array, as it takes two
	 * characters to represent any given byte. An exception is thrown if the passed char array has an
	 * odd number of elements.
	 * @param data An string containing hexadecimal digits
	 * @return A byte string containing binary data decoded from the supplied string.
	 * @throws IllegalArgumentException Thrown if an odd number or illegal of characters is supplied
	 */
	public static ByteString fromHexString(String data) {
		return fromHexChars(data.toCharArray());
	}

	/**
	 * Converts a hexadecimal character to an integer.
	 * @param ch A character to convert to an integer digit
	 * @param index The index of the character in the source
	 * @return An integer
	 * @throws IllegalArgumentException Thrown if ch is an illegal hex character
	 */
	private static int toDigit(char ch, int index) {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new IllegalArgumentException("Illegal hexadecimal character " + ch + " at index " + index);
		}
		return digit;
	}

	// =================================================================
	// ByteString -> byte[]

	/**
	 * Copies bytes into a buffer at the given offset.
	 * @param target buffer to copy into
	 * @param offset in the target buffer
	 */
	public void copyTo(final byte[] target, final int offset) {
		System.arraycopy(bytes, 0, target, offset, bytes.length);
	}

	/**
	 * Copies bytes into a buffer.
	 * @param target buffer to copy into
	 * @param sourceOffset offset within these bytes
	 * @param targetOffset offset within the target buffer
	 * @param size number of bytes to copy
	 */
	public void copyTo(final byte[] target, final int sourceOffset, final int targetOffset, final int size) {
		System.arraycopy(bytes, sourceOffset, target, targetOffset, size);
	}

	/**
	 * Copies bytes into a ByteBuffer.
	 * @param target ByteBuffer to copy into.
	 * @throws ReadOnlyBufferException if the {@code target} is read-only
	 * @throws BufferOverflowException if the {@code target}'s remaining() space is not large enough
	 *           to hold the data.
	 */
	public void copyTo(ByteBuffer target) {
		target.put(bytes, 0, bytes.length);
	}

	/**
	 * Copies bytes to a {@code byte[]}.
	 */
	public byte[] toByteArray() {
		final int size = bytes.length;
		final byte[] copy = new byte[size];
		System.arraycopy(bytes, 0, copy, 0, size);
		return copy;
	}

	/**
	 * Constructs a new read-only {@code java.nio.ByteBuffer} with the same backing byte array.
	 */
	public ByteBuffer asReadOnlyByteBuffer() {
		final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		return byteBuffer.asReadOnlyBuffer();
	}

	/**
	 * Constructs a new {@code String} by decoding the bytes using the specified charset.
	 */
	public String toString(final String charsetName) throws UnsupportedEncodingException {
		return new String(bytes, charsetName);
	}

	/**
	 * Constructs a new {@code String} by decoding the bytes as UTF-8.
	 */
	public String toStringUtf8() {
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 not supported?", e);
		}
	}

	/**
	 * Constructs a new {@code String} by encoding the bytes in hexadecimal.
	 */
	public String toHexString(boolean lowercase) {
		return toHexString(lowercase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	/**
	 * Constructs a new {@code String} by encoding the bytes in hexadecimal.
	 */
	public String toHexString() {
		return toHexString(true);
	}

	private String toHexString(char[] toDigits) {
		final int length = 2 * bytes.length;
		final StringBuilder b = new StringBuilder(length);
		for (int i = 0; i < bytes.length; i++) {
			b.append(toDigits[(0xF0 & bytes[i]) >>> 4]);
			b.append(toDigits[0x0F & bytes[i]]);
		}
		return b.toString();
	}

	private String toHexString(char[] toDigits, int maxBytes) {
		final int nBytes = Math.min(maxBytes, bytes.length);
		final int length = 2 * nBytes;
		final StringBuilder b = new StringBuilder(length);
		for (int i = 0; i < nBytes; i++) {
			b.append(toDigits[(0xF0 & bytes[i]) >>> 4]);
			b.append(toDigits[0x0F & bytes[i]]);
		}
		return b.toString();
	}

	/**
	 * Default toString = toHexString (max 32 chars)
	 */
	public String toString() {
		if (bytes.length <= 16) {
			return toHexString();
		}
		return toHexString(DIGITS_LOWER, 14) + "...";
	}

	// =================================================================
	// equals() and hashCode()

	@Override
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof ByteString)) {
			return false;
		}

		final ByteString other = (ByteString) o;
		final int size = bytes.length;
		if (size != other.bytes.length) {
			return false;
		}

		final byte[] thisBytes = bytes;
		final byte[] otherBytes = other.bytes;
		for (int i = 0; i < size; i++) {
			if (thisBytes[i] != otherBytes[i]) {
				return false;
			}
		}

		return true;
	}

	private volatile int hash = 0;

	@Override
	public int hashCode() {
		int h = hash;

		if (h == 0) {
			final byte[] thisBytes = bytes;
			final int size = bytes.length;

			h = size;
			for (int i = 0; i < size; i++) {
				h = h * 31 + thisBytes[i];
			}
			if (h == 0) {
				h = 1;
			}

			hash = h;
		}

		return h;
	}

	// =================================================================
	// Input stream

	/**
	 * Creates an {@code InputStream} which can be used to read the bytes.
	 */
	public InputStream newInput() {
		return new ByteArrayInputStream(bytes);
	}

	// =================================================================
	// Output stream

	/**
	 * Creates a new {@link Output} with the given initial capacity.
	 */
	public static Output newOutput(final int initialCapacity) {
		return new Output(new ByteArrayOutputStream(initialCapacity));
	}

	/**
	 * Creates a new {@link Output}.
	 */
	public static Output newOutput() {
		return newOutput(32);
	}

	/**
	 * Builder based on an output stream. Call {@link #build()} to create the {@code ByteString}
	 * instance.
	 */
	public static final class Output extends FilterOutputStream implements Builder<ByteString> {
		private final ByteArrayOutputStream bout;

		/**
		 * Constructs a new output with the given initial capacity.
		 */
		private Output(final ByteArrayOutputStream bout) {
			super(bout);
			this.bout = bout;
		}

		/**
		 * Creates a {@code ByteString} instance from this {@code Output}.
		 */
		public ByteString build() {
			final byte[] byteArray = bout.toByteArray();
			return new ByteString(byteArray);
		}
	}

	// =================================================================
	// Serialization proxy

	private static class SerializationProxy implements Serializable {
		/** Serial UID. */
		private static final long serialVersionUID = 3486210277801140074L;
		private final byte[] bytes;

		public SerializationProxy(ByteString s) {
			this.bytes = s.bytes;
		}

		private Object readResolve() {
			return ByteString.copyFrom(bytes);
		}
	}

	private Object writeReplace() {
		return new SerializationProxy(this);
	}

	private void readObject(ObjectInputStream stream) throws InvalidObjectException {
		throw new InvalidObjectException("Proxy required");
	}
}
