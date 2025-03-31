package ba.unsa.etf.book.api.service;

import ba.unsa.etf.book.api.model.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> findAll();

    Reservation findById(Long id);

    Reservation create(Reservation reservation);

    Reservation update(Reservation reservation);

    void delete(Long id);
}
