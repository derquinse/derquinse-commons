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

	@Test
	public void loren() throws IOException {
		Map<String, byte[]> map = ZipFiles.loadZip(getClass().getResourceAsStream("loren.zip"));
		assertEquals(3, map.size());
		assertTrue(map.get("loren1.txt").length > 10);
		assertTrue(map.get("loren2.txt").length > 10);
		assertTrue(map.get("folder/loren3.txt").length > 10);
		assertNull(map.get("loren.kk"));
	}

	@Test
	public void size() throws IOException {
		byte[] input = RandomSupport.getBytes(1);
		byte[] output = ZipFiles.gzip(input);
		System.out.println(output.length);
	}


}
