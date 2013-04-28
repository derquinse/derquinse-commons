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
package net.derquinse.common.io;

import static com.google.common.base.Preconditions.checkArgument;
import static net.derquinse.common.io.MemoryByteSourceLoader.get;
import static org.testng.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.io.ByteStreams;
import com.google.common.io.Closer;

/**
 * Tests for memoery byte sources.
 * @author Andres Rodriguez
 */
public class MemoryByteSourceTest {
	/** Random source. */
	private final SecureRandom random = new SecureRandom();

	/** Data generator. */
	private byte[] data(int length) {
		final byte[] bytes = new byte[length];
		random.nextBytes(bytes);
		return bytes;
	}

	/** Assertion. */
	private void equals(byte[] actual, byte[] expected, String test, String test2, String test3) {
		Assert.assertEquals(actual, expected, String.format("%s / %s / %s", test, test2, test3));
	}

	/** Checks a memory source. */
	private void check(String test, String test2, byte[] original, MemoryByteSource source) throws IOException {
		Closer closer = Closer.create();
		try {
			InputStream is1 = closer.register(source.openStream());
			equals(ByteStreams.toByteArray(is1), original, test, test2, "First IS Read");
			InputStream is2 = closer.register(source.openStream());
			equals(ByteStreams.toByteArray(is2), original, test, test2, "Second IS Read");
		} finally {
			closer.close();
		}
		equals(source.read(), original, test, test2, "First Read");
		equals(source.read(), original, test, test2, "Second Read");
	}

	private void check(String test, byte[] original, MemoryByteSource source) throws IOException {
		check(test, "Basic", original, source);
		check(test, "Merge", original, source.merge());
		check(test, "Heap", original, source.toHeap(false));
		check(test, "Heap Merged", original, source.toHeap(true));
		check(test, "Direct", original, source.toDirect(false));
		check(test, "Direct Merged", original, source.toDirect(true));
	}

	/** Check Kind. */
	private void checkKind(String test, String subtest, boolean flag) {
		assertTrue(flag, String.format("%s: %s", test, subtest));
	}

	/** Test maker. */
	private void test(String test, byte[] original, MemoryByteSource source, boolean direct, int chunks)
			throws IOException {
		checkKind(test, "Direct Flag", direct == source.isDirect());
		checkKind(test, "Heap Flag", direct == !source.isHeap());
		checkKind(test, "Number of chunks", chunks == source.chunks());
		check(test, original, source);
	}

	/** Test maker. */
	private void test(String test, int length, MemoryByteSourceLoader loader, boolean direct, int chunks)
			throws IOException {
		byte[] original = data(length);
		MemoryByteSource source = loader.load(new ByteArrayInputStream(original));
		test(test, original, source, direct, chunks);
		MemoryByteSink sink = loader.newSink();
		sink.write(original);
		MemoryByteSource source2 = sink.queue().remove();
		test(test + " Sink ", original, source2, direct, chunks);
	}

	/** Exerciser. */
	private void exercise(boolean direct, int chunkSize) throws IOException {
		checkArgument(chunkSize >= 1024);
		MemoryByteSourceLoader loader = get().direct(direct).chunkSize(chunkSize);
		String base = String.format("%s(%s) ", direct ? "Direct" : "Heap", chunkSize);
		test(base + "less than one", chunkSize / 2 + 3, loader, direct, 1);
		test(base + "one", chunkSize, loader, direct, 1);
		test(base + "more than one", chunkSize + 111, loader, direct, 2);
		test(base + "two", chunkSize * 2, loader, direct, 2);
		test(base + "three", chunkSize * 3, loader, direct, 3);
		test(base + "many", chunkSize * 7 + chunkSize / 3, loader, direct, 8);
		test(base + "max size, single chunk", chunkSize / 2, loader.maxSize(chunkSize / 2), direct, 1);
		test(base + "max size, many chunk", chunkSize * 5, loader.maxSize(chunkSize * 5), direct, 5);
		MemoryByteSourceLoader merged = loader.merge(true);
		base += "merged ";
		test(base + "less than one", chunkSize / 2 + 3, merged, direct, 1);
		test(base + "one", chunkSize, merged, direct, 1);
		test(base + "more than one", chunkSize + 111, merged, direct, 1);
		test(base + "two", chunkSize * 2, merged, direct, 1);
		test(base + "three", chunkSize * 3, merged, direct, 1);
		test(base + "many", chunkSize * 7 + chunkSize / 3, merged, direct, 1);
		test(base + "max size, single chunk", chunkSize / 2, merged.maxSize(chunkSize / 2), direct, 1);
		test(base + "max size, many chunk", chunkSize * 5, merged.maxSize(chunkSize * 5), direct, 1);

	}

	/**
	 * Heap default chunk size.
	 */
	@Test
	public void heapDefault() throws IOException {
		exercise(false, 8192);
	}

	/**
	 * Heap custom chunk size.
	 */
	@Test
	public void heapCustom() throws IOException {
		exercise(false, 19137);
	}

	/**
	 * Direct default chunk size.
	 */
	@Test
	public void directDefault() throws IOException {
		exercise(true, 8192);
	}

	/**
	 * Direct custom chunk size.
	 */
	@Test
	public void directCustom() throws IOException {
		exercise(true, 19139);
	}

	/**
	 * Empty heap.
	 */
	@Test
	public void heapEmpty() throws IOException {
		test("Heap Empty", 0, get().direct(false).chunkSize(8192), false, 0);
	}

	/**
	 * Empty direct.
	 */
	@Test
	public void heapDirect() throws IOException {
		test("Direct Empty", 0, get().direct(true), true, 0);
	}

	/**
	 * More than maximum size.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void heapOver() throws IOException {
		test("Heap overflow", 456, get().maxSize(384), false, 0);
	}

	/**
	 * More than maximum size.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void heapOverChunked() throws IOException {
		test("Heap overflow chuncked", 456, get().chunkSize(128).maxSize(384), false, 0);
	}

	/**
	 * More than maximum size.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void directOver() throws IOException {
		test("Direct overflow", 456, get().direct(true).maxSize(384), true, 0);
	}

	/**
	 * More than maximum size.
	 */
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void directOverChunked() throws IOException {
		test("Direct overflow chuncked", 456, get().direct(true).chunkSize(128).maxSize(384), true, 0);
	}

}
