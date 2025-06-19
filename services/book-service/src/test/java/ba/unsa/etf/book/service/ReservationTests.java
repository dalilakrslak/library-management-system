package ba.unsa.etf.book.service;

import ba.unsa.etf.book.api.model.Reservation;
import ba.unsa.etf.book.api.model.ReservationWithUser;
import ba.unsa.etf.book.api.model.User;
import ba.unsa.etf.book.core.impl.ReservationServiceImpl;
import ba.unsa.etf.book.core.mapper.ReservationMapper;
import ba.unsa.etf.book.core.validation.ReservationValidation;
import ba.unsa.etf.book.dao.model.BookEntity;
import ba.unsa.etf.book.dao.model.BookVersionEntity;
import ba.unsa.etf.book.dao.model.ReservationEntity;
import ba.unsa.etf.book.dao.repository.BookVersionRepository;
import ba.unsa.etf.book.dao.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private BookVersionRepository bookVersionRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private ReservationEntity reservationEntity;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        BookEntity bookEntity = new BookEntity(1L, "Title", "Description", 300, 2022, "English", null, null);
        BookVersionEntity bookVersionEntity = new BookVersionEntity("123-456-789", bookEntity, false, false, 1L);
        reservationEntity = new ReservationEntity(1L, 1L, bookVersionEntity, LocalDate.now());
        reservation = new Reservation(1L, 2L, "123-456-789", LocalDate.now());
    }

    @Test
    void testCreate() {
        BookEntity bookEntity = new BookEntity(1L, "Title", "Description", 300, 2022, "English", null, null);
        BookVersionEntity bookVersionEntity = new BookVersionEntity("123-456-789", bookEntity, false, false, 1L);

        when(reservationMapper.dtoToEntity(any(Reservation.class))).thenReturn(reservationEntity);
        when(reservationRepository.save(any(ReservationEntity.class))).thenReturn(reservationEntity);
        when(reservationMapper.entityToDto(any(ReservationEntity.class))).thenReturn(reservation);
        when(bookVersionRepository.findByIsbn("123-456-789")).thenReturn(Optional.of(bookVersionEntity));
        when(bookVersionRepository.save(any(BookVersionEntity.class))).thenReturn(bookVersionEntity);

        Reservation result = reservationService.create(reservation);

        assertNotNull(result);
        assertEquals(reservation.getId(), result.getId());
        assertEquals(reservation.getUserId(), result.getUserId());
        assertEquals(reservation.getBookVersion(), result.getBookVersion());

        verify(reservationRepository).save(any(ReservationEntity.class));

        assertTrue(bookVersionEntity.getIsReserved());
        verify(bookVersionRepository).save(bookVersionEntity);
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

    @Test
    void testGetAllReservationsWithUserInfo() {
        ReservationEntity entity = reservationEntity;
        User user = new User(1L, "Dalila", "Test", "dalila@test.com", "061/220550");
        Reservation dto = reservation;

        when(reservationRepository.findAll()).thenReturn(List.of(entity));
        when(reservationMapper.entityToDto(entity)).thenReturn(dto);
        when(restTemplate.getForObject("http://library-service/user/" + entity.getUserId(), User.class))
                .thenReturn(user);

        List<ReservationWithUser> result = reservationService.getAllReservationsWithUserInfo();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user.getFirstName(), result.get(0).getUser().getFirstName());
        assertEquals(dto.getId(), result.get(0).getReservation().getId());
    }
}
