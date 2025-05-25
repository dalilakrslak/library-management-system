package ba.unsa.etf.transfer.dao.repository;

import ba.unsa.etf.transfer.dao.model.BookVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookVersionRepository extends JpaRepository<BookVersionEntity, String> {
}
