package ba.unsa.etf.book.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE = "book.reserve.queue";
    public static final String EXCHANGE = "book.exchange";
    public static final String ROUTING_KEY = "book.reserve";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
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
