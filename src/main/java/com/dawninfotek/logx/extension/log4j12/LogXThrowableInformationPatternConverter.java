package com.dawninfotek.logx.extension.log4j12;

public class LogXThrowableInformationPatternConverter {

	private int maxLines = Integer.MAX_VALUE;

	private LogXThrowableInformationPatternConverter(final String[] options) {
		if ((options != null) && (options.length > 0)) {
			if ("none".equals(options[0])) {
				maxLines = 0;
			} else if ("short".equals(options[0])) {
				maxLines = 1;
			} else {
				try {
					maxLines = Integer.parseInt(options[0]);
				} catch (NumberFormatException ex) {
				}
			}
		}
	}

	public static LogXThrowableInformationPatternConverter newInstance(final String[] options) {
		return new LogXThrowableInformationPatternConverter(options);
	}

	public boolean handlesThrowable() {
		return true;
	}
}
