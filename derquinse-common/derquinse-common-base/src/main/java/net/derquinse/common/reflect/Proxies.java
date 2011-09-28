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
package net.derquinse.common.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Utility methods for Dynamic Proxies.
 * @author Andres Rodriguez
 */
public final class Proxies {
	/**
	 * Not instantiable.
	 */
	private Proxies() {
		throw new AssertionError();
	}

	public static <T> T alwaysNull(Class<T> type) {
		return type.cast(Proxy.newProxyInstance(Proxies.class.getClassLoader(), new Class<?>[] { type }, AlwaysNull.INSTANCE));
	}

	public static Object alwaysNull(Class<?>... interfaces) {
		return Proxy.newProxyInstance(Proxies.class.getClassLoader(), interfaces, AlwaysNull.INSTANCE);
	}

	public static <T> T unsupported(Class<T> type) {
		return type.cast(Proxy.newProxyInstance(Proxies.class.getClassLoader(), new Class<?>[] { type }, Unsupported.INSTANCE));
	}

	public static Object unsupported(Class<?>... interfaces) {
		return Proxy.newProxyInstance(Proxies.class.getClassLoader(), interfaces, Unsupported.INSTANCE);
	}
	
	/** Always null invocation handler. */
	private enum AlwaysNull implements InvocationHandler {
		INSTANCE;
		
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			return null;
		}
		
		@Override
		public String toString() {
			return "Always NULL Proxy";
		}
	}

	/** Unsupported operation invocation handler. */
	private enum Unsupported implements InvocationHandler {
		INSTANCE;
		
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public String toString() {
			return "UnsupportedOperationException Proxy";
		}
	}
	
}
