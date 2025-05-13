package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.dao.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("FROM UserEntity u WHERE u.firstName = :firstName AND u.lastName = :lastName")
    List<UserEntity> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query("FROM UserEntity u WHERE u.email = :email")
    UserEntity findByEmail(@Param("email") String email);
}
