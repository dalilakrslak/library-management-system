package ba.unsa.etf.transfer.dao.repository;

import ba.unsa.etf.transfer.dao.model.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
}
