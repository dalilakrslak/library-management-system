package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.dao.model.ReservationEntity;
import ba.unsa.etf.book.dao.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findReservationsByUserId(Long userId);
}
