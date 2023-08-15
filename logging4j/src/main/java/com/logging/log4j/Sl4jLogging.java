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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vinay.sharma
 *
 */
public class Sl4jLogging {
  private static Logger logger = LoggerFactory.getLogger(Sl4jLogging.class);

  public static void main(String[] args) {
    logger.info("Hello");
  }
}
