package ba.unsa.etf.transfer.dao.repository;

import ba.unsa.etf.transfer.dao.model.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<TransferEntity, Long> {
    List<TransferEntity> findByBookVersionInAndTransferDateIsNull(List<String> bookVersions);
}
