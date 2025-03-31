package ba.unsa.etf.book.core.impl;

import ba.unsa.etf.book.api.model.Reservation;
import ba.unsa.etf.book.api.service.ReservationService;
import ba.unsa.etf.book.core.mapper.ReservationMapper;
import ba.unsa.etf.book.core.validation.ReservationValidation;
import ba.unsa.etf.book.dao.model.ReservationEntity;
import ba.unsa.etf.book.dao.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final ReservationValidation reservationValidation;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Reservation findById(Long id) {
        reservationValidation.exists(id);
        return reservationRepository.findById(id).map(reservationMapper::entityToDto).orElse(null);
    }

    @Override
    @Transactional
    public Reservation create(Reservation reservation) {
        ReservationEntity reservationEntity = reservationMapper.dtoToEntity(reservation);
        reservationRepository.save(reservationEntity);
        return reservationMapper.entityToDto(reservationEntity);
    }

    @Override
    @Transactional
    public Reservation update(Reservation reservation) {
        if (!reservationRepository.existsById(reservation.getId())) {
            return null;
        }
        ReservationEntity reservationEntity = reservationMapper.dtoToEntity(reservation);
        reservationRepository.save(reservationEntity);
        return reservationMapper.entityToDto(reservationEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        reservationValidation.exists(id);
        reservationRepository.deleteById(id);
    }
}
