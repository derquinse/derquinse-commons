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

import net.derquinse.common.test.RandomSupport;

import org.testng.annotations.Test;

/**
 * Tests for {@link ZipFiles}
 * @author Andres Rodriguez
 */
public class ZipFilesTest {
	private Map<String, byte[]> items;

	@Test
	public void loren() throws IOException {
		items = ZipFiles.loadZip(getClass().getResourceAsStream("loren.zip"));
		assertEquals(3, items.size());
		assertTrue(items.get("loren1.txt").length > 10);
		assertTrue(items.get("loren2.txt").length > 10);
		assertTrue(items.get("folder/loren3.txt").length > 10);
		assertNull(items.get("loren.kk"));
	}

	@Test(dependsOnMethods = "loren")
	public void maybe() throws IOException {
		Map<String, MaybeCompressed<byte[]>> maybe = ZipFiles.loadZipAndGZip(getClass().getResourceAsStream("loren.zip"));
		assertEquals(maybe.size(), items.size());
		for (String k : items.keySet()) {
			byte[] orig = items.get(k);
			MaybeCompressed<byte[]> m = maybe.get(k);
			byte[] payload = m.getPayload();
			byte[] cmp;
			if (m.isCompressed()) {
				assertTrue(payload.length < orig.length);
				System.out.printf("%s reduced from %d to %d\n", k, orig.length, payload.length);
				cmp = ZipFiles.gunzip(payload);
			} else {
				cmp = payload;
			}
			assertEquals(cmp, orig);
		}
	}

	@Test
	public void size() throws IOException {
		byte[] input = RandomSupport.getBytes(1);
		byte[] output = ZipFiles.gzip(input);
		System.out.println(output.length);
	}

}
