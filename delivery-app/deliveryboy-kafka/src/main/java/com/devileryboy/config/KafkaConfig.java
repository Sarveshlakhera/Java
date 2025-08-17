package com.devileryboy.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import com.devileryboy.AppConstants;

@Configuration
public class KafkaConfig {

  @Bean
  public NewTopic topic() {
    return TopicBuilder.name(AppConstants.LOCATION_TOPIC_NAME)
        // .partitions()
        // .replicas(0)
        .build();
  }
}
