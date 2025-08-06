package br.com.alura.ecommerce;

import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class NewOrderMain {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// no KafkaProducer preciso de parametros de tipagem tipo da chave e o tipo da
		// mensagem nesse caso vamos usar string em tudo
		try (var dispatcher = new KafkaDispatcher<Order>()) {
			for (int i = 0; i < 10; i++) {
				String userId = UUID.randomUUID().toString();
				String orderId = UUID.randomUUID().toString();
				var amount = new BigDecimal(Math.random() * 5000 + 1);
				var order = new Order(userId, orderId, amount);
				String value = key + "132123,67523,789289745";
				dispatcher.send("ECOMMERCE_NEW_ORDER", userId, order);

				var email = "Thank you for your order! We are processing it now.";
				dispatcher.send("ECOMMERCE_SEND_EMAIL", userId, email);
			}
		}
	}

}
