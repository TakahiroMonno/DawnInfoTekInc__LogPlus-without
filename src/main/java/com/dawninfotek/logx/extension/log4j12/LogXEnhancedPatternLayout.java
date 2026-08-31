package com.dawninfotek.logx.extension.log4j12;

public class LogXEnhancedPatternLayout {
  public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";

  public static final String TTCC_CONVERSION_PATTERN =
    "%r [%t] %p %c %x - %m%n";

  private String conversionPattern;
  private boolean handlesExceptions;

  public LogXEnhancedPatternLayout() {
    this(DEFAULT_CONVERSION_PATTERN);
  }

  public LogXEnhancedPatternLayout(final String pattern) {
    this.conversionPattern = pattern;
    this.handlesExceptions = pattern != null && pattern.contains("%throwable");
  }

  public void setConversionPattern(final String conversionPattern) {
    this.conversionPattern = conversionPattern;
    this.handlesExceptions = conversionPattern != null && conversionPattern.contains("%throwable");
  }

  public String getConversionPattern() {
    return conversionPattern;
  }

  public void activateOptions() {
  }

  public boolean ignoresThrowable() {
    return !handlesExceptions;
  }
}
