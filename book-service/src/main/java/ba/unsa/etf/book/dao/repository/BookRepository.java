package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.dao.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
