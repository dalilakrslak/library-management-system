package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.api.model.Author;
import ba.unsa.etf.book.dao.model.AuthorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    @Query("FROM AuthorEntity a WHERE LOWER(a.biography) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<AuthorEntity> findByBiographyKeyword(@Param("keyword") String keyword);

    @Query("FROM AuthorEntity a WHERE " +
            "LOWER(a.firstName) LIKE LOWER(CONCAT('%', COALESCE(:firstName, ''), '%')) " +
            "AND LOWER(a.lastName) LIKE LOWER(CONCAT('%', COALESCE(:lastName, ''), '%')) " +
            "AND LOWER(a.biography) LIKE LOWER(CONCAT('%', COALESCE(:biography, ''), '%'))")
    List<AuthorEntity> searchAuthors(@Param("firstName") String firstName,
                                     @Param("lastName") String lastName,
                                     @Param("biography") String biography);
}
