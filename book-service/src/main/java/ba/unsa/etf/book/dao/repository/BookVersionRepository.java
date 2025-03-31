package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.dao.model.BookVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookVersionRepository extends JpaRepository<BookVersionEntity, String> {
}
