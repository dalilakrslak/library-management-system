package ba.unsa.etf.transfer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "book.exchange";
    public static final String ROUTING_KEY = "book.reserve";
    public static final String QUEUE = "book.reserve.queue";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue successQueue() {
        return new Queue("book.success.queue");
    }

    @Bean
    public Queue rollbackQueue() {
        return new Queue("book.rollback.queue");
    }

    @Bean
    public Binding successBinding() {
        return BindingBuilder
                .bind(successQueue())
                .to(exchange())
                .with("book.reserve.success");
    }

    @Bean
    public Binding rollbackBinding() {
        return BindingBuilder
                .bind(rollbackQueue())
                .to(exchange())
                .with("book.reserve.rollback");
    }
}
