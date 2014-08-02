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

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.common.io.ByteProcessor;
import com.google.common.io.ByteSource;

/**
 * Utility class for dealing with message digests.
 * @author Andres Rodriguez
 */
public final class Digests extends NotInstantiable {
	/** Not instantiable. */
	private Digests() {
	}

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
	public static MessageDigest getInstance(String algorithm) throws NoSuchAlgorithmException {
		checkNotNull(algorithm, "The digest algorithm must be provided");
		return MessageDigest.getInstance(algorithm);
	}

	/**
	 * Returns a MessageDigest object that implements the specified digest algorithm.
	 * @param algorithm the name of the algorithm requested.
	 * @return A Digest object that implements the specified algorithm.
	 * @throws RuntimeException (with {@link NoSuchAlgorithmException} cause) if the specified
	 *           algorithm is not provided.
	 */
	private static MessageDigest getInstanceUnchecked(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(String.format("No %s algorithm", algorithm), e);
		}
	}

	/** Computes and returns as a byte string the digest of the provided data. */
	public static ByteString getDigest(byte[] data, MessageDigest md) {
		return ByteString.copyFrom(md.digest(data));
	}

	/**
	 * Computes and returns as a byte string the digest of the provided data.
	 */
	public static ByteString getDigest(ByteSource source, final MessageDigest md) throws IOException {
		checkNotNull(source);
		checkNotNull(md);
		final byte[] digest = source.read(new ByteProcessor<byte[]>() {
			@Override
			public boolean processBytes(byte[] buf, int off, int len) {
				md.update(buf, off, len);
				return true;
			}

			@Override
			public byte[] getResult() {
				return md.digest();
			}
		});
		return ByteString.copyFrom(digest);
	}

	/**
	 * Returns a Digest object that implements the MD5 algorithm.
	 */
	public static MessageDigest md5() {
		return getInstanceUnchecked(MD5);
	}

	/** Computes and returns as a byte string the MD5 digest of the provided data. */
	public static ByteString md5(byte[] data) {
		return getDigest(data, md5());
	}

	/** Computes and returns as a byte string the MD5 digest of the provided data. */
	public static ByteString md5(ByteSource source) throws IOException {
		return getDigest(source, md5());
	}

	/**
	 * Returns a Digest object that implements the SHA-1 algorithm.
	 */
	public static MessageDigest sha1() {
		return getInstanceUnchecked(SHA1);
	}

	/** Computes and returns as a byte string the SHA-1 digest of the provided data. */
	public static ByteString sha1(byte[] data) {
		return getDigest(data, sha1());
	}

	/** Computes and returns as a byte string the SHA-1 digest of the provided data. */
	public static ByteString sha1(ByteSource source) throws IOException {
		return getDigest(source, sha1());
	}

	/**
	 * Returns a Digest object that implements the SHA-256 algorithm.
	 */
	public static MessageDigest sha256() {
		return getInstanceUnchecked(SHA256);
	}

	/** Computes and returns as a byte string the SHA-256 digest of the provided data. */
	public static ByteString sha256(byte[] data) {
		return getDigest(data, sha256());
	}

	/** Computes and returns as a byte string the SHA-256 digest of the provided data. */
	public static ByteString sha256(ByteSource source) throws IOException {
		return getDigest(source, sha256());
	}

	/**
	 * Returns a Digest object that implements the SHA-512 algorithm.
	 */
	public static MessageDigest sha512() {
		return getInstanceUnchecked(SHA512);
	}

	/** Computes and returns as a byte string the SHA-512 digest of the provided data. */
	public static ByteString sha512(byte[] data) {
		return getDigest(data, sha512());
	}

	/** Computes and returns as a byte string the SHA-512 digest of the provided data. */
	public static ByteString sha512(ByteSource source) throws IOException {
		return getDigest(source, sha512());
	}

}
