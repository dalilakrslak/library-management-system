package ba.unsa.etf.book.utils;

import ba.unsa.etf.book.api.model.BookReserveRequest;
import ba.unsa.etf.book.config.RabbitMQConfig;
import ba.unsa.etf.book.dao.repository.BookVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookReserveListener {

    private final BookVersionRepository bookVersionRepository;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleReserveRequest(BookReserveRequest request) {
        System.out.println("📥 [BookService] Received reserve request: " + request);

        try {
            bookVersionRepository.findByIsbnAndLibraryId(request.getIsbn(), request.getLibraryFrom())
                    .filter(bv -> Boolean.FALSE.equals(bv.getIsReserved()))
                    .ifPresentOrElse(
                            bv -> {
                                bv.setIsReserved(true);
                                bookVersionRepository.save(bv);

                                System.out.println("✅ Book reserved: " + bv.getIsbn());
                                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,
                                        "book.reserve.success",
                                        request);
                            },
                            () -> {
                                System.out.println("❌ Book already reserved or not found. Rolling back.");
                                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,
                                        "book.reserve.rollback",
                                        request);
                            }
                    );
        } catch (Exception e) {
            System.err.println("🔥 Error handling reserve request: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
