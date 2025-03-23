package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.dao.model.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
}
