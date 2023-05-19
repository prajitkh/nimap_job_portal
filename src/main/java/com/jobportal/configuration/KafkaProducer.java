package com.jobportal.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

	public static final String USER = "userBulk";

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void addUsersToUsersMainTable(String object) {
		System.err.println("Kafka producer********");
		kafkaTemplate.send(USER, object);

	}

}
