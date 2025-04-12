package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.dao.model.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findReservationsByUserId(Long userId);

    @Override
    @Query("SELECT r FROM ReservationEntity r JOIN FETCH r.user JOIN FETCH r.bookVersion")
    List<ReservationEntity> findAll();
}
