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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;

import net.derquinse.common.io.MemoryByteSource;
import net.derquinse.common.io.MemoryByteSourceLoader;
import net.derquinse.common.test.RandomSupport;

import org.testng.annotations.Test;

/**
 * Tests for {@link ZipFileLoader} and {@link LoadedZipFile}
 * @author Andres Rodriguez
 */
public class ZipFilesTest {
	private LoadedZipFile items;

	@Test
	public void loren() throws IOException {
		items = ZipFileLoader.get().load(getClass().getResourceAsStream("loren.zip"));
		assertEquals(3, items.size());
		assertTrue(items.get("loren1.txt").size() > 10);
		assertTrue(items.get("loren2.txt").size() > 10);
		assertTrue(items.get("folder/loren3.txt").size() > 10);
		assertNull(items.get("loren.kk"));
	}

	@Test(dependsOnMethods = "loren")
	public void maybe() throws IOException {
		Map<String, MaybeCompressed<MemoryByteSource>> maybe = items.maybeGzip();
		assertEquals(maybe.size(), items.size());
		for (String k : items.keySet()) {
			MemoryByteSource orig = items.get(k);
			MaybeCompressed<MemoryByteSource> m = maybe.get(k);
			MemoryByteSource payload = m.getPayload();
			MemoryByteSource cmp;
			if (m.isCompressed()) {
				assertTrue(payload.size() < orig.size());
				System.out.printf("%s reduced from %d to %d\n", k, orig.size(), payload.size());
				cmp = MemoryByteSourceLoader.get().transformer(GZIP.decompression()).load(payload);
			} else {
				cmp = payload;
			}
			assertTrue(cmp.contentEquals(orig));
		}
	}
}
