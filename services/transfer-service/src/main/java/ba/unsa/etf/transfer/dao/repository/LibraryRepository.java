package ba.unsa.etf.transfer.dao.repository;

import ba.unsa.etf.transfer.dao.model.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {
}
