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
package net.derquinse.common.collect;

import javax.annotation.Nullable;

/**
 * Visitor for hierarchy traversal.
 * @author Andres Rodriguez
 * @param <E> Type of the elements in the hierarchy.
 */
public interface HierarchyVisitor<E> {
	/**
	 * Visits an entry.
	 * @param element Element.
	 * @param parent Parent, {@code null} if the element is in the first level.
	 * @param index Index in parent's children list.
	 */
	void visit(E element, @Nullable E parent, int index);
}
