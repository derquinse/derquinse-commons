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
package net.derquinse.common.gson;

import static org.testng.Assert.assertNull;
import net.derquinse.common.base.ByteString;
import net.derquinse.common.test.GsonSerializabilityTests;
import net.derquinse.common.test.RandomSupport;

import org.testng.annotations.Test;

import com.google.common.hash.HashCode;

/**
 * Tests for {@link GsonHashCode}.
 * @author Andres Rodriguez
 */
public class GsonHashCodeTest {
	/** Serializability. */
	@Test
	public void serializability() {
		HashCode h = ByteString.copyFrom(RandomSupport.getBytes(512)).toHashCode();
		GsonSerializabilityTests.check(DerquinseGson.get(), h);
	}

	/** The empty string is null. */
	@Test
	public void empty() {
		String json = DerquinseGson.get().toJson(ByteString.EMPTY);
		HashCode h = DerquinseGson.get().fromJson(json, HashCode.class);
		assertNull(h);
	}

}
