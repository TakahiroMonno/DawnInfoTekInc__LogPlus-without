package com.dawninfotek.logx.extension.log4j2;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;

/**
 * Log4j 2 equivalent of the log4j 1.x pair
 * {@link com.dawninfotek.logx.extension.log4j12.LogXThrowableInformationPatternConverter}
 * and {@link com.dawninfotek.logx.extension.log4j12.LogXThrowableRenderer}: it
 * renders the throwable's stack trace flattened onto a single line (no line
 * separators between frames) so it can be embedded in single-line/JSON-style
 * layouts, optionally truncated to a number of stack frames.
 *
 * Registered under the {@code logXThrowable} conversion word rather than
 * overriding the built-in {@code %throwable} so it only applies where a
 * pattern explicitly opts into it.
 */
@Plugin(name = "LogXThrowablePatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({ "logXThrowable" })
public final class LogXThrowablePatternConverter extends LogEventPatternConverter {

	private int maxLines = Integer.MAX_VALUE;

	private LogXThrowablePatternConverter(final String[] options) {
		super("LogXThrowable", "logXThrowable");

		if (options != null && options.length > 0) {
			if ("none".equals(options[0])) {
				maxLines = 0;
			} else if ("short".equals(options[0])) {
				maxLines = 1;
			} else {
				try {
					maxLines = Integer.parseInt(options[0]);
				} catch (NumberFormatException ex) {
					// keep default (unlimited) on invalid option
				}
			}
		}
	}

	public static LogXThrowablePatternConverter newInstance(final String[] options) {
		return new LogXThrowablePatternConverter(options);
	}

	@Override
	public void format(final LogEvent event, final StringBuilder toAppendTo) {
		if (maxLines == 0) {
			return;
		}

		final Throwable thrown = event.getThrown();
		if (thrown == null) {
			return;
		}

		final String[] lines = renderLines(thrown);

		int length = lines.length;
		if (maxLines < 0) {
			length += maxLines;
		} else if (length > maxLines) {
			length = maxLines;
		}

		for (int i = 0; i < length; i++) {
			toAppendTo.append(lines[i]);
		}
	}

	@Override
	public boolean handlesThrowable() {
		return true;
	}

	private String[] renderLines(final Throwable thrown) {
		final StringWriter sw = new StringWriter();
		thrown.printStackTrace(new PrintWriter(sw));
		return sw.toString().split("\r\n|\r|\n");
	}
}
