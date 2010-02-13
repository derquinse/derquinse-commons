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
package net.derquinse.common.i18n;

import static com.google.common.base.Preconditions.checkNotNull;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * A localized formatted message.
 * @author Andres Rodriguez
 */
public final class LocalizedMessage extends AbstractLocalized<String> {
	private static final Object[] EMPTY = new Object[0];
	/** Pattern. */
	private final Localized<String> pattern;
	/** Arguments. */
	private final Object[] args;
	/** If any of the arguments is itself localized. */
	private final boolean nested;

	/**
	 * Default constructor.
	 * @param pattern Pattern.
	 * @param args Arguments.
	 */
	public LocalizedMessage(Localized<String> pattern, Object... args) {
		checkNotNull(pattern, "A message pattern must be provided");
		if (args == null) {
			this.args = EMPTY;
			this.nested = false;
		} else {
			boolean localized = false;
			for (Object o : args) {
				if (o instanceof Localized<?>) {
					localized = true;
					break;
				}
			}
			this.args = args;
			this.nested = localized;
		}
		this.pattern = pattern;
	}

	/*
	 * (non-Javadoc)
	 * @see net.derquinse.common.i18n.Localized#get(java.util.Locale)
	 */
	public String get(Locale locale) {
		try {
			final String text = pattern.get(locale);
			if (args.length == 0) {
				return text;
			}
			final Object[] arguments;
			if (nested) {
				arguments = new Object[args.length];
				for (int i = 0; i < args.length; i++) {
					final Object o = args[i];
					if (o instanceof Localized<?>) {
						arguments[i] = ((Localized<?>) o).get(locale);
					} else {
						arguments[i] = o;
					}
				}
			} else {
				arguments = args;
			}
			return MessageFormat.format(text, arguments);
		} catch (IllegalArgumentException e) {
			throw new UnableToLocalizeException(e);
		}
	}
}
