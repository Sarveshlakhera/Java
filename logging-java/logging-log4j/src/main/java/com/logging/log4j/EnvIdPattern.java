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
@Plugin(name = "EnvIdPattern", category = LogEventPatternConverter.CATEGORY)
@ConverterKeys({"envId"})
public class EnvIdPattern extends LogEventPatternConverter {

  /**
   * @param name
   * @param style
   */
  protected EnvIdPattern(String[] options) {
    super("EnvIdPattern", null);
  }
  
  /**
   * Obtains an instance of pattern converter.
   *
   * @param options options, may be null.
   * @return instance of pattern converter.
   */
  public static EnvIdPattern newInstance(final String[] options) {
      return new EnvIdPattern(options);
  }

  @Override
  public void format(LogEvent event, StringBuilder toAppendTo) {
    toAppendTo.append("23423");
  }
}
