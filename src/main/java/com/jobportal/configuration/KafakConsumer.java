package com.jobportal.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableKafka
public class KafakConsumer {
	private static final Logger LOG = LoggerFactory.getLogger(KafakConsumer.class);

	@Autowired
	private ObjectMapper objectMapper;

	@KafkaListener(topics = "userBulk", groupId = "bulkUpload")
	public void checkToString(String string) throws JsonMappingException, JsonProcessingException {

		{
			Object readValue = objectMapper.readValue(string, Object.class);

			System.err.println("Kakfa listener");
			System.err.println("Kafka " + readValue.toString());

		}

	}

}
