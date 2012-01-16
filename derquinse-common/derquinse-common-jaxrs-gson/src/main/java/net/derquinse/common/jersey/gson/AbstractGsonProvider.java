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
package net.derquinse.common.jersey.gson;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.gson.Gson;

/**
 * Abstract base class from Gson-based JSON JAX-RS providers.
 * @author Andres Rodriguez.
 */
abstract class AbstractGsonProvider {
	/** Gson supplier. */
	private final Supplier<Gson> supplier;

	/** Default constructor. */
	AbstractGsonProvider() {
		this.supplier = Suppliers.ofInstance(new Gson());
	}

	/**
	 * Constructor.
	 * @param supplier Gson supplier to use.
	 */
	AbstractGsonProvider(Supplier<Gson> supplier) {
		this.supplier = checkNotNull(supplier, "The Gson supplier must be provided");
	}

	/** Returns the Gson object to use. */
	final Gson getGson() {
		return checkNotNull(supplier.get(), "The provided Gson object is null");
	}
}
