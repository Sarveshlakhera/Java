package com.logging.logback.convertors;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * 
 * @author sarvesh.lakhera
 *
 */
public class EnvIDClassicConvertor extends ClassicConverter {

  @Override
  public String convert(ILoggingEvent event) {
    return "87654";
  }
}
