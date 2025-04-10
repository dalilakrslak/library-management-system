package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.api.model.Author;
import ba.unsa.etf.book.dao.model.AuthorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
