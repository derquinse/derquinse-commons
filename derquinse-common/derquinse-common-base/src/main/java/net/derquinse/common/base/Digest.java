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
package net.derquinse.common.base;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.common.io.NullOutputStream;

/**
 * Utility class for dealing with message digests. Does not provide mutable acces to the computed
 * digest.
 * @author Andres Rodriguez
 */
public final class Digest {
	/** Algorithm: MD5. */
	public static final String MD5 = "MD5";
	/** Algorithm: SHA-1. */
	public static final String SHA1 = "SHA-1";
	/** Algorithm: SHA-256. */
	public static final String SHA256 = "SHA-256";
	/** Algorithm: SHA-512. */
	public static final String SHA512 = "SHA-512";

	/**
	 * Creates a new instance.
	 * @param algorithm Digest algorithm.
	 * @return A Digest object that implements the specified algorithm.
	 * @throws NoSuchAlgorithmException If the algorithm is not available in the caller's environment.
	 */
	public static Digest newDigest(String algorithm) throws NoSuchAlgorithmException {
		checkNotNull(algorithm, "The digest algorithm must be provided");
		return new Digest(MessageDigest.getInstance(algorithm));
	}

	/**
	 * Returns a MessageDigest object that implements the specified digest algorithm.
	 * @param algorithm the name of the algorithm requested.
	 * @return A Digest object that implements the specified algorithm.
	 * @throws RuntimeException (with {@link NoSuchAlgorithmException} cause) if the specified
	 *           algorithm is not provided.
	 */
	private static Digest getInstanceUnchecked(String algorithm) {
		try {
			return new Digest(MessageDigest.getInstance(algorithm));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(String.format("No %s algorithm", algorithm), e);
		}
	}

	/**
	 * Returns a Digest object that implements the MD5 algorithm.
	 */
	public static Digest md5() {
		return getInstanceUnchecked(MD5);
	}

	/**
	 * Returns a Digest object that implements the SHA-1 algorithm.
	 */
	public static Digest sha1() {
		return getInstanceUnchecked(SHA1);
	}

	/**
	 * Returns a Digest object that implements the SHA-256 algorithm.
	 */
	public static Digest sha256() {
		return getInstanceUnchecked(SHA256);
	}

	/**
	 * Returns a Digest object that implements the SHA-512 algorithm.
	 */
	public static Digest sha512() {
		return getInstanceUnchecked(SHA512);
	}

	/** Internal message digest. */
	private final MessageDigest digest;

	/** Constructor. */
	private Digest(final MessageDigest digest) {
		this.digest = digest;
	}

	/**
	 * Updates the digest using the specified byte.
	 * @param input the byte with which to update the digest.
	 * @return This object for method chaining.
	 */
	public Digest update(byte input) {
		digest.update(input);
		return this;
	}

	/**
	 * Updates the digest using the specified array of bytes, starting at the specified offset.
	 * @param input the array of bytes.
	 * @param offset the offset to start from in the array of bytes.
	 * @param len the number of bytes to use, starting at <code>offset</code>.
	 * @return This object for method chaining.
	 */
	public Digest update(byte[] input, int offset, int len) {
		digest.update(input, offset, len);
		return this;
	}

	/**
	 * Updates the digest using the specified array of bytes.
	 * @param input the array of bytes.
	 * @return This object for method chaining.
	 */
	public Digest update(byte[] input) {
		digest.update(input);
		return this;
	}

	/**
	 * Update the digest using the specified ByteBuffer. The digest is updated using the
	 * <code>input.remaining()</code> bytes starting at <code>input.position()</code>. Upon return,
	 * the buffer's position will be equal to its limit; its limit will not have changed.
	 * @param input the ByteBuffer
	 * @return This object for method chaining.
	 */
	public Digest update(ByteBuffer input) {
		digest.update(input);
		return this;
	}

	/** Returns the digest as a byte string. */
	public ByteString toByteString() {
		return ByteString.copyFrom(digest.digest());
	}

	/** Returns the digest as an input stream. */
	public InputStream toInputStream() {
		return new ByteArrayInputStream(digest.digest());
	}

	/**
	 * Returns the digest as a read-only {@code java.nio.ByteBuffer}.
	 */
	public ByteBuffer asReadOnlyByteBuffer() {
		final ByteBuffer byteBuffer = ByteBuffer.wrap(digest.digest());
		return byteBuffer.asReadOnlyBuffer();
	}

	/** Returns an output stream that updates the current digest. */
	public OutputStream getFilterOutputStream(OutputStream stream) {
		checkNotNull(stream, "The output stream must be provided");
		return new FilterOutputStream(new DigestOutputStream(stream, digest));
	}

	/** Returns an output stream that updates the current digest using a null output stream. */
	public OutputStream getFilterOutputStream() {
		return new FilterOutputStream(new DigestOutputStream(new NullOutputStream(), digest));
	}

	/** Returns an input stream that updates the current digest. */
	public InputStream getFilterInputStream(InputStream stream) {
		checkNotNull(stream, "The input stream must be provided");
		return new FilterInputStream(new DigestInputStream(stream, digest)) {
		};
	}
}
