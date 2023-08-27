package com.logging.log4j;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

/**
 * 
 * @author sarvesh.lakhera
 *
 */
@Plugin(name = "UserIdPattern", category = LogEventPatternConverter.CATEGORY)
@ConverterKeys({ "userId" })
public class UserIdPattern extends LogEventPatternConverter {

  /**
   * @param name
   * @param style
   */
  protected UserIdPattern(String[] options) {
    super("UserIdPattern", null);
  }

  /**
   * Obtains an instance of pattern converter.
   *
   * @param options options, may be null.
   * @return instance of pattern converter.
   */
  public static UserIdPattern newInstance(final String[] options) {
    return new UserIdPattern(options);
  }

  @Override
  public void format(LogEvent event, StringBuilder toAppendTo) {
    toAppendTo.append("sarvesh@22");
  }
}
