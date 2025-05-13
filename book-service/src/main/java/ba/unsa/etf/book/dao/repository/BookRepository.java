package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.dao.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Query("FROM BookEntity b WHERE b.author.firstName LIKE %:authorName% OR b.author.lastName LIKE %:authorName%")
    List<BookEntity> findBooksByAuthor(String authorName);
}
