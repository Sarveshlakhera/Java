package com.logging.logback.convertors;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * 
 * @author sarvesh.lakhera
 *
 */
public class AccountIDClassicConvertor extends ClassicConverter {

  @Override
  public String convert(ILoggingEvent event) {
    return "account@1223l";
  }
}
