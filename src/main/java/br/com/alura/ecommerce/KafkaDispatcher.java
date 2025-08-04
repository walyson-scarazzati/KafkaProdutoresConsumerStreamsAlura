package br.com.alura.ecommerce;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaDispatcher implements Closeable {
	
	private KafkaProducer<String, String> producer;

	KafkaDispatcher() {
		this.producer = new KafkaProducer<String, String>(properties());
	}
	
	 static Properties properties() {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return properties;
	}

	 void send(String topic, String key, String value) throws InterruptedException, ExecutionException {
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties());
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);
		  Callback callback = (data, ex) -> {
		        if (ex != null) {
		            ex.printStackTrace();
		            return;
		        }
		        System.out.println("sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset " + data.offset() + "/ timestamp " + data.timestamp());
		    };
		producer.send(record, callback).get();
	}

	@Override
	public void close() {
		producer.close();
	}

}
