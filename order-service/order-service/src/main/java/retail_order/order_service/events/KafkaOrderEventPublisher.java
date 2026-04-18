package retail_order.order_service.events;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.retail.common.events.OrderCreatedEvent;
@Component
public class KafkaOrderEventPublisher implements OrderEventPublisher {
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private final String topic = "order-created";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(KafkaOrderEventPublisher.class);

    public KafkaOrderEventPublisher(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(OrderCreatedEvent event) {
       log.info("Publishing OrderCreatedEvent to Kafka for orderId: {}", event.orderId());
       kafkaTemplate.send(topic, event.orderId().toString(), event);  
     }

}
