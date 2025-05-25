package ba.unsa.etf.library.dao.repository;

import ba.unsa.etf.library.dao.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
