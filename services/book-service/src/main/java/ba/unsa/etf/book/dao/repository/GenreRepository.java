package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.dao.model.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    List<GenreEntity> findByName(String name);
}
