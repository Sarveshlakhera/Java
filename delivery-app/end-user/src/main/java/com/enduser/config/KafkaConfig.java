package com.enduser.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.enduser.AppConstants;

@Configuration
public class KafkaConfig {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @KafkaListener(topics = AppConstants.LOCATION_TOPIC_NAME, groupId = AppConstants.GROUP_ID)
  public void updateLocation(String value) {
    logger.info("Consumed topic : {} with value: {}", AppConstants.LOCATION_TOPIC_NAME, value);
  }
}
