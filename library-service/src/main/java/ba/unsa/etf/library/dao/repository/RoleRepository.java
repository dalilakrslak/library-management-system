package ba.unsa.etf.library.dao.repository;

import ba.unsa.etf.library.dao.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
