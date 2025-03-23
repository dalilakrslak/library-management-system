package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.dao.model.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
}
