package rado.alberto.org.kafka.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import rado.alberto.org.kafka.events.OrderCreatedEvent;

@Component
public class OrderCreatedConsumer {

    @KafkaListener(
            topics = "order.created",
            groupId = "pedidos-group"
    )
    public void listen(OrderCreatedEvent event) {
        System.out.println("Pedido creado recibido por Kafka: " + event);
    }
}