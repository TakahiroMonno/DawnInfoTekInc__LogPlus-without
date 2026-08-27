package com.dawninfotek.logx.extension.log4j2;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.message.Message;

/**
 * Log4j 2 equivalent of the log4j 1.x
 * {@link com.dawninfotek.logx.extension.log4j12.LogXMessagePatternConverter}.
 * Registered under the {@code logXMessage} conversion word (rather than
 * overriding the built-in {@code %m}) so it does not silently change message
 * formatting for patterns that do not opt into it.
 */
@Plugin(name = "LogXMessagePatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({ "logXMessage" })
public final class LogXMessagePatternConverter extends LogEventPatternConverter {

	private static final LogXMessagePatternConverter INSTANCE = new LogXMessagePatternConverter();

	private LogXMessagePatternConverter() {
		super("LogXMessage", "logXMessage");
	}

	public static LogXMessagePatternConverter newInstance(final String[] options) {
		return INSTANCE;
	}

	@Override
	public void format(final LogEvent event, final StringBuilder toAppendTo) {
		final Message message = event.getMessage();
		String msg = message == null ? null : message.getFormattedMessage();
		if (msg == null) {
			return;
		}

		// remove all '\t' and '\r\n', replace '\n' with spaces
		msg = msg.replaceAll("\\t", "").replaceAll("\\r\\n", "").replaceAll("\\n", "    ");

		toAppendTo.append(msg);
	}
}
