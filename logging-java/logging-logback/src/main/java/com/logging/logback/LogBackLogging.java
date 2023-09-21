package com.logging.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author sarvesh.lakhera
 *
 */
public class LogBackLogging {
private static Logger logger = LoggerFactory.getLogger(LogBackLogging.class);
  
  public static void main(String[] args) {
    logger.info("Hello");
  }
}
