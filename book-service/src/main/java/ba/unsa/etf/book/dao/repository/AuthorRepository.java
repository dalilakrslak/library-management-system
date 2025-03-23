package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.dao.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
}
