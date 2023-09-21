package com.logging.log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author sarvesh.lakhera
 *
 */
public class Sl4jLogging {
  private static Logger logger = LoggerFactory.getLogger(Sl4jLogging.class);

  public static void main(String[] args) {
    logger.info("Hello");
  }
}
