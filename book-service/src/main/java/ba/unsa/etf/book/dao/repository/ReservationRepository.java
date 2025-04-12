package ba.unsa.etf.book.dao.repository;

import ba.unsa.etf.book.dao.model.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    @Query("SELECT r FROM ReservationEntity r JOIN FETCH r.bookVersion JOIN FETCH r.user WHERE r.user.id = :userId")
    List<ReservationEntity> findReservationsByUserId(Long userId);

}
