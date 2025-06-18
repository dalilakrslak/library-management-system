package ba.unsa.etf.transfer.utils;

import ba.unsa.etf.transfer.api.model.BookReserveRequest;
import ba.unsa.etf.transfer.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishReserveRequest(BookReserveRequest request) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                request
        );
    }
}