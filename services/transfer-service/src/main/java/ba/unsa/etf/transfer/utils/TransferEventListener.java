package ba.unsa.etf.transfer.utils;

import ba.unsa.etf.transfer.api.model.BookReserveRequest;
import ba.unsa.etf.transfer.dao.repository.TransferRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferEventListener {

    private final TransferRepository transferRepository;

    @RabbitListener(queues = "book.success.queue")
    public void handleSuccess(BookReserveRequest request) {
        System.out.println("✅ Rezervacija uspješna za ISBN: " + request.getIsbn());
    }

    @Transactional
    @RabbitListener(queues = "book.rollback.queue")
    public void handleRollback(BookReserveRequest request) {
        System.out.println("⛔ Rollback pozvan za ISBN: " + request.getIsbn());
        transferRepository.deleteLastTransferForBook(
                request.getIsbn(),
                request.getLibraryFrom(),
                request.getLibraryTo()
        );
    }
}
