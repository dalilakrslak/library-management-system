package ba.unsa.etf.book;

import ba.unsa.etf.book.api.model.Reservation;
import ba.unsa.etf.book.core.impl.ReservationServiceImpl;
import ba.unsa.etf.book.core.mapper.ReservationMapper;
import ba.unsa.etf.book.core.validation.ReservationValidation;
import ba.unsa.etf.book.dao.model.BookEntity;
import ba.unsa.etf.book.dao.model.BookVersionEntity;
import ba.unsa.etf.book.dao.model.ReservationEntity;
import ba.unsa.etf.book.dao.model.UserEntity;
import ba.unsa.etf.book.dao.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationTests {
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private ReservationValidation reservationValidation;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private ReservationEntity reservationEntity;
    private Reservation reservation;
    private BookVersionEntity bookVersionEntity;
    private BookEntity bookEntity;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        bookEntity = new BookEntity(1L, "Title", "Description", 300, 2022, "English", null, null);
        bookVersionEntity = new BookVersionEntity("123-456-789", bookEntity, false, false);
        userEntity = new UserEntity(1L, "John", "Doe", "johndoe@email.com", "password", "060123456");
        reservationEntity = new ReservationEntity(1L, userEntity, bookVersionEntity, LocalDate.now());
        reservation = new Reservation(1L, 2L, "123-456-789", LocalDateTime.now());
    }

    @Test
    void testCreate() {
        when(reservationMapper.dtoToEntity(any(Reservation.class))).thenReturn(reservationEntity);
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservationEntity);
        when(reservationMapper.entityToDto(any(ReservationEntity.class))).thenReturn(reservation);

        Reservation result = reservationService.create(reservation);

        assertNotNull(result);
        assertEquals(reservation.getId(), result.getId());
        assertEquals(reservation.getUserId(), result.getUserId());
        assertEquals(reservation.getBookVersion(), result.getBookVersion());
        verify(reservationRepository).save(any(ReservationEntity.class));
    }

    @Test
    void testFindById() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservationEntity));
        when(reservationMapper.entityToDto(any(ReservationEntity.class))).thenReturn(reservation);

        Reservation result = reservationService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testDelete() {
        Long id = 1L;
        doNothing().when(reservationRepository).deleteById(id);

        reservationService.delete(id);

        verify(reservationValidation).exists(id);
        verify(reservationRepository).deleteById(id);
    }
}
