package ba.unsa.etf.book.core.impl;

import ba.unsa.etf.book.api.model.Reservation;
import ba.unsa.etf.book.api.model.ReservationWithUser;
import ba.unsa.etf.book.api.model.User;
import ba.unsa.etf.book.api.service.ReservationService;
import ba.unsa.etf.book.core.mapper.ReservationMapper;
import ba.unsa.etf.book.core.validation.ReservationValidation;
import ba.unsa.etf.book.dao.model.ReservationEntity;
import ba.unsa.etf.book.dao.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final ReservationValidation reservationValidation;
    private final RestTemplate restTemplate;

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

    @Override
    public Page<Reservation> getAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable).map(reservationMapper::entityToDto);
    }

    @Override
    public List<Reservation> createBatch(List<Reservation> reservations) {
        List<ReservationEntity> entities = reservations.stream().map(reservationMapper::dtoToEntity).collect(Collectors.toList());
        entities.addAll(reservationRepository.saveAll(entities));
        return entities.stream().map(reservationMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findReservationsByUserId(Long userId) {
        List<ReservationEntity> entities = reservationRepository.findReservationsByUserId(userId);
        return entities.stream().map(reservationMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<ReservationWithUser> getAllReservationsWithUserInfo() {
        List<ReservationEntity> entities = reservationRepository.findAll();

        return entities.stream().map(entity -> {
            String url = "http://library-service/user/" + entity.getUserId();
            User user = restTemplate.getForObject(url, User.class);

            Reservation reservation = reservationMapper.entityToDto(entity);
            return new ReservationWithUser(reservation, user);
        }).collect(Collectors.toList());
    }

}
