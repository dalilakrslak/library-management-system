package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.dao.model.BookVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookVersionRepository extends JpaRepository<BookVersionEntity, String> {
    @Query("FROM BookVersionEntity b WHERE b.book.title LIKE %:title%")
    List<BookVersionEntity> findBooksByTitle(String title);

    List<BookVersionEntity> findByBookId(Long bookId);

    Optional<BookVersionEntity> findByIsbnAndLibraryId(String isbn, Long libraryId);

    List<BookVersionEntity> findByBookIdAndLibraryId(Long bookId, Long libraryId);

    List<BookVersionEntity> findByLibraryId(Long libraryId);

    Optional<BookVersionEntity> findByIsbn(String isbn);
}
