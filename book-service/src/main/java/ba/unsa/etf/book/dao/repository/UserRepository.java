package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.dao.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
