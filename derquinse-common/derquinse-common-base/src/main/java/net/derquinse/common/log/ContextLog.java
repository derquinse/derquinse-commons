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
package net.derquinse.common.log;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An SLF4J logger with an optional hierarchical context and {@link String#format} formatting.
 * @author Andres Rodriguez
 */
public final class ContextLog {
	/** Logger to use. */
	private final Logger logger;
	/** Current context. */
	private final String context;

	/** Returns a context log based on the provided logger. */
	public static ContextLog of(Logger logger) {
		return new ContextLog(logger);
	}

	/** Returns a context log based on a logger with the provided name. */
	public static ContextLog of(String name) {
		return new ContextLog(LoggerFactory.getLogger(name));
	}

	/** Returns a context log based on a logger named after the provided class. */
	public static ContextLog of(Class<?> clazz) {
		return new ContextLog(LoggerFactory.getLogger(clazz));
	}

	/** Constructor. */
	private ContextLog(Logger logger) {
		this.logger = checkNotNull(logger, "The source logger must be provided.");
		this.context = null;
	}

	/** Nested contructor. */
	private ContextLog(ContextLog parent, String context) {
		checkNotNull(context, "The nested context must be provided.");
		this.logger = parent.logger;
		if (parent.context == null) {
			this.context = context;
		} else {
			this.context = parent.context + '/' + context;
		}
	}

	/** Decorates a log message. */
	private String decorate(String msg, Object... args) {
		if (msg == null) {
			return null;
		}
		final boolean hasArgs = args != null && args.length > 0;
		if (context != null) {
			msg = new StringBuilder(context.length() + 2 + msg.length()).append(context).append(": ").append(msg).toString();
		}
		if (hasArgs) {
			return String.format(msg, args);
		}
		return msg;
	}

	/** Adds an aditional context. */
	public ContextLog to(@Nullable String context) {
		if (context == null || context.length() == 0) {
			return this;
		}
		return new ContextLog(this, context);
	}

	/** Return whether the logger instance enabled for the TRACE level. */
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	/** Log a message at the TRACE level. */
	public void trace(String msg, Object... args) {
		if (logger.isTraceEnabled()) {
			logger.trace(decorate(msg, args));
		}
	}

	/** Log a message at the TRACE level. */
	public void trace(Throwable t, String msg, Object... args) {
		if (logger.isTraceEnabled()) {
			logger.trace(decorate(msg, args), t);
		}
	}

	/** Return whether the logger instance enabled for the DEBUG level. */
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	/** Log a message at the DEBUG level. */
	public void debug(String msg, Object... args) {
		if (logger.isDebugEnabled()) {
			logger.debug(decorate(msg, args));
		}
	}

	/** Log a message at the DEBUG level. */
	public void debug(Throwable t, String msg, Object... args) {
		if (logger.isDebugEnabled()) {
			logger.debug(decorate(msg, args), t);
		}
	}

	/** Return whether the logger instance enabled for the INFO level. */
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	/** Log a message at the INFO level. */
	public void info(String msg, Object... args) {
		if (logger.isInfoEnabled()) {
			logger.info(decorate(msg, args));
		}
	}

	/** Log a message at the INFO level. */
	public void info(Throwable t, String msg, Object... args) {
		if (logger.isInfoEnabled()) {
			logger.info(decorate(msg, args), t);
		}
	}

	/** Return whether the logger instance enabled for the WARN level. */
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	/** Log a message at the WARN level. */
	public void warn(String msg, Object... args) {
		if (logger.isWarnEnabled()) {
			logger.warn(decorate(msg, args));
		}
	}

	/** Log a message at the WARN level. */
	public void warn(Throwable t, String msg, Object... args) {
		if (logger.isWarnEnabled()) {
			logger.warn(decorate(msg, args), t);
		}
	}

	/** Return whether the logger instance enabled for the ERROR level. */
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	/** Log a message at the ERROR level. */
	public void error(String msg, Object... args) {
		if (logger.isErrorEnabled()) {
			logger.error(decorate(msg, args));
		}
	}

	/** Log a message at the ERROR level. */
	public void error(Throwable t, String msg, Object... args) {
		if (logger.isErrorEnabled()) {
			logger.error(decorate(msg, args), t);
		}
	}

}
