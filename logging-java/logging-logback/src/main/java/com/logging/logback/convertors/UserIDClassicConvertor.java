package com.logging.logback.convertors;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * 
 * @author sarvesh.lakhera
 *
 */
public class UserIDClassicConvertor extends ClassicConverter {

  @Override
  public String convert(ILoggingEvent event) {
    return "sarvesh@32";
  }
}
