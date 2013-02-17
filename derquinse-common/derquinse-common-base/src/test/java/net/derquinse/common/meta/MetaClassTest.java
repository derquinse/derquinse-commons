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
package net.derquinse.common.meta;

import static net.derquinse.common.meta.MetaClass.ROOT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.testng.annotations.Test;

/**
 * Tests for {@link MetaClass}
 * @author Andres Rodriguez
 */
public class MetaClassTest {
	/** Root metaclass. */
	@Test
	public void root() {
		assertTrue(ROOT.isRoot());
		assertEquals(ROOT.getSuperclass(), ROOT);
		assertTrue(ROOT.getSuperinterfaces().isEmpty());
		assertTrue(ROOT.getDeclaredFields().isEmpty());
		assertTrue(ROOT.getFields().isEmpty());
	}

	@Test
	public void empty() {
		MetaClass<Empty> mc = MetaClass.of(Empty.class);
		assertFalse(mc.isRoot());
		assertEquals(mc.getSuperclass(), ROOT);
		assertTrue(mc.getSuperinterfaces().isEmpty());
		assertTrue(mc.getDeclaredFields().isEmpty());
		assertTrue(mc.getFields().isEmpty());
	}

	@Test
	public void simple() {
		MetaClass<SimpleClass> mc = MetaClass.of(SimpleClass.class);
		assertFalse(mc.isRoot());
		assertEquals(mc.getSuperclass(), ROOT);
		assertTrue(mc.getSuperinterfaces().isEmpty());
		assertEquals(mc.getDeclaredFields().size(), 1);
		assertEquals(mc.getFields().size(), 1);
	}

	@Test
	public void sample() {
		MetaClass<SampleProperty> mc = MetaClass.of(SampleProperty.class);
		assertFalse(mc.isRoot());
		assertEquals(mc.getSuperclass(), ROOT);
		assertTrue(mc.getSuperinterfaces().isEmpty());
		assertEquals(mc.getDeclaredFields().size(), 1);
		assertEquals(mc.getFields().size(), 1);
	}

	@Test
	public void sample2() {
		MetaClass<Sample2Property> mc = MetaClass.of(Sample2Property.class);
		assertFalse(mc.isRoot());
		assertEquals(mc.getSuperclass(), ROOT);
		assertTrue(mc.getSuperinterfaces().isEmpty());
		assertEquals(mc.getDeclaredFields().size(), 1);
		assertEquals(mc.getFields().size(), 1);
	}
	
	interface I1 {
	}

	interface I2 {
	}

	interface I3 extends I2 {
	}

	class R {
	}

	class A implements I1 {
	}

	class B extends A implements I2 {
	}

	class C extends B implements I3 {
	}

	class D implements I3 {
	}

	private void print(Class<?> klass) {
		System.out.println(klass.getName());
		if (klass.getSuperclass() != null) {
			System.out.println("\t" + klass.getSuperclass().getName());
		}
		for (Class<?> i : Arrays.asList(klass.getInterfaces())) {
			System.out.println("\t\t" + i.getName());
		}
	}

	@Test
	public void supers() {
		print(A.class);
		print(B.class);
		print(C.class);
		print(D.class);
		print(I3.class);
	}
}
