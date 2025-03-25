package ba.unsa.etf.transfer.dao.repository;

import ba.unsa.etf.transfer.dao.model.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<TransferEntity, Long> {
}
