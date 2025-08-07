package br.com.alura.ecommerce;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

public class LogService {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		var logService = new LogService();
		try (KafkaService service = new KafkaService(LogService.class.getSimpleName(), Pattern.compile("ECOMMERCE.*"),
				logService::parse, String.class,
				Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))) {
			service.run();
		}
	}

	public void parse(ConsumerRecord<String, String> record) {
		System.out.println("-----------------");
		System.out.println("LOG: " + record.topic());
		System.out.println("key: " + record.key());
		System.out.println("value: " + record.value());
		System.out.println("partition: " + record.partition());
		System.out.println("Offset: " + record.offset());
	}

}
