/*
 * Copyright 2008-2010 the original author or authors.
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
package net.derquinse.common.product;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;

/**
 * Iterable view of a product.
 * @author Andres Rodriguez
 */
final class ProductIterable implements Iterable<Object> {
	/** Product to iterate. */
	private final Product product;

	ProductIterable(final Product product) {
		this.product = checkNotNull(product, "The product to iterate must be provided");
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Object> iterator() {
		return new ProductIterator(product);
	}

	@Override
	public String toString() {
		return product.toString();
	}
}
