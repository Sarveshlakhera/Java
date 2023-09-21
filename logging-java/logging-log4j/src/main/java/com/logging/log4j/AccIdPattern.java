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
@Plugin(name = "AccIdPattern", category = LogEventPatternConverter.CATEGORY)
@ConverterKeys({ "accId" })
public class AccIdPattern extends LogEventPatternConverter {

  /**
   * @param name
   * @param style
   */
  protected AccIdPattern(String[] options) {
    super("AccIdPattern", null);
  }

  /**
   * Obtains an instance of pattern converter.
   *
   * @param options options, may be null.
   * @return instance of pattern converter.
   */
  public static AccIdPattern newInstance(final String[] options) {
    return new AccIdPattern(options);
  }

  @Override
  public void format(LogEvent event, StringBuilder toAppendTo) {
    toAppendTo.append("acc0729L");
  }
}
