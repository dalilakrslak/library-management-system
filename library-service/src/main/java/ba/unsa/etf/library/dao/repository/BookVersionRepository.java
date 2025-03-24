package ba.unsa.etf.library.dao.repository;

import ba.unsa.etf.library.dao.model.BookVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookVersionRepository extends JpaRepository<BookVersionEntity, Long> {
}
