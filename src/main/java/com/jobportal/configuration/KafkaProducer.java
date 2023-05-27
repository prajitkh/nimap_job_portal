package com.jobportal.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

	public static final String USER = "userBulk";
	private static Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void addUsersToUsersMainTable(String object) {
		System.err.println("Kafka producer********");
		LOGGER.debug("========topic Name===== " + object + "=========message=======" + object);

		kafkaTemplate.send(USER, object);

	}

}
