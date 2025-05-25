package ba.unsa.etf.library.dao.repository;

import ba.unsa.etf.library.dao.model.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {
}
