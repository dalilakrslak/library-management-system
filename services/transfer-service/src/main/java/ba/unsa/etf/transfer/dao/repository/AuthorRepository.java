package ba.unsa.etf.transfer.dao.repository;

import ba.unsa.etf.transfer.dao.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
