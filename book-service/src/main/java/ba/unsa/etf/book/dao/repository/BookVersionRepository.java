package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.dao.model.BookVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookVersionRepository extends JpaRepository<BookVersionEntity, String> {
    @Query("FROM BookVersionEntity b WHERE b.book.title LIKE %:title%")
    List<BookVersionEntity> findBooksByTitle(String title);

    @Override
    @Query("SELECT bv FROM BookVersionEntity bv JOIN FETCH bv.book b JOIN FETCH b.author a JOIN FETCH b.genre g")
    List<BookVersionEntity> findAll();


}
