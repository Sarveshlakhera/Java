package com.devileryboy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.devileryboy.AppConstants;

@Service
public class KafkaService {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  public boolean updateLocation(String location) {
    logger.info("Producing location of topic: {} with location: {}", "location-update-topic", location);
    this.kafkaTemplate.send(AppConstants.LOCATION_TOPIC_NAME, location);
    return true;
  }
}
