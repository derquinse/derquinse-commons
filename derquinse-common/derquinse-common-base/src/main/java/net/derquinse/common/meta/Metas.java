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

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nullable;

import net.derquinse.common.base.NotInstantiable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Support methods for property and flag descriptors.
 * @author Andres Rodriguez
 */
public final class Metas extends NotInstantiable {
	/** Not instantiable. */
	private Metas() {
	}

	/**
	 * Creates an instance of ToStringHelper, which is an enriched Objects.ToStringHelper with support
	 * for properties and flags.
	 * @see Objects#toStringHelper(Object)
	 * @param self The object to generate the string for.
	 */
	public static <C> ToStringHelper<C> toStringHelper(C self) {
		return new ToStringHelper<C>(self);
	}

	/**
	 * Support class for {@link Metas#toStringHelper}.
	 * @author Jason Lee
	 * @param <C> Containing type.
	 */
	public static final class ToStringHelper<C> {
		private final MoreObjects.ToStringHelper helper;
		private final C object;

		/**
		 * Use {@link Metas#toStringHelper(Object)} to create an instance.
		 */
		private ToStringHelper(C object) {
			this.object = checkNotNull(object);
			this.helper = MoreObjects.toStringHelper(object);
		}

		/**
		 * Adds a name/value pair to the formatted output in {@code name=value} format. If {@code value}
		 * is {@code null}, the string {@code "null"} is used.
		 */
		public ToStringHelper<C> add(String name, @Nullable Object value) {
			helper.add(name, value);
			return this;
		}

		/**
		 * Adds a name/value pair to the formatted output based on the provided property.
		 */
		public ToStringHelper<C> add(MetaProperty<? super C, ?> property) {
			helper.add(property.getName(), property.apply(object));
			return this;
		}

		/**
		 * Adds a name/value pair to the formatted output based on the provided flag.
		 */
		public ToStringHelper<C> add(MetaFlag<? super C> flag) {
			helper.add(flag.getName(), flag.apply(object));
			return this;
		}

		/**
		 * Adds a value to the formatted output in {@code value} format.
		 * <p>
		 * It is strongly encouraged to use {@link #add(String, Object)} instead and give value a
		 * readable name.
		 */
		public ToStringHelper<C> addValue(@Nullable Object value) {
			helper.addValue(value);
			return this;
		}

		/**
		 * Returns a string in the format specified by {@link Objects#toStringHelper(Object)}.
		 */
		@Override
		public String toString() {
			return helper.toString();
		}
	}

}
