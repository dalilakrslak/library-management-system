package ba.unsa.etf.book.api.service;

import ba.unsa.etf.book.api.model.Reservation;
import ba.unsa.etf.book.api.model.ReservationWithUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {
    List<Reservation> findAll();

    Reservation findById(Long id);

    Reservation create(Reservation reservation);

    Reservation update(Reservation reservation);

    void delete(Long id);

    Page<Reservation> getAllReservations(Pageable pageable);

    List<Reservation> createBatch(List<Reservation> reservations);

    List<Reservation> findReservationsByUserId(Long userId);

    List<ReservationWithUser> getAllReservationsWithUserInfo();
}
