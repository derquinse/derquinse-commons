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
package net.derquinse.common.stats;

import net.derquinse.common.math.PartialRealFunction;

/**
 * Populations factory and helper methods.
 * @author Andres Rodriguez
 */
public final class Populations {
	/** Not instantiable. */
	private Populations() {
		throw new AssertionError();
	}

	public static LongPopulation ofLong() {
		return LongPopulationImpl.EMPTY;
	}

	public static PartialRealFunction normalQuantile(LongPopulation population) {
		return new NormalLongQuantile(population);
	}

}
