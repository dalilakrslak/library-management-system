package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.dao.model.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    List<LoanEntity> findByUserId(Long UserId);
}
