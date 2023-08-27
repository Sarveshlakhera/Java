/** Copyright © 2022 Jitterbit, Inc.
 *
 * Licensed under the JITTERBIT MASTER SUBSCRIPTION AGREEMENT
 * (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * https://www.jitterbit.com/cloud-eula
 *
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.logging.log4j;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

/**
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
