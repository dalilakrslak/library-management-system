package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Reservation;
import ba.unsa.etf.book.api.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ReservationRestTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationRest reservationRest;

    private Reservation reservation;
    private List<Reservation> reservations;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservation = new Reservation(
                1L,
                100L,
                "978-99955-42-10-4",
                LocalDateTime.of(2024, 5, 8, 12, 0)
        );
        reservations = List.of(reservation);
    }

    @Test
    void findAll_ShouldReturnListOfReservations() {
        when(reservationService.findAll()).thenReturn(reservations);
        List<Reservation> result = reservationRest.findAll();
        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).getUserId());
    }

    @Test
    void findById_ShouldReturnReservation() {
        when(reservationService.findById(1L)).thenReturn(reservation);
        Reservation result = reservationRest.findById(1L).getBody();
        assertNotNull(result);
        assertEquals("978-99955-42-10-4", result.getBookVersion());
    }

    @Test
    void delete_ShouldReturnConfirmationMessage() {
        doNothing().when(reservationService).delete(1L);
        var response = reservationRest.delete(1L);
        verify(reservationService).delete(1L);
        assertEquals("Reservation with ID 1 was deleted successfully.", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void findReservationsByUserId_ShouldReturnUserReservations() {
        when(reservationService.findReservationsByUserId(100L)).thenReturn(reservations);
        ResponseEntity<List<Reservation>> result = reservationRest.findReservationsByUserId(100L);
        assertEquals(1, result.getBody().size());
        assertEquals("978-99955-42-10-4", result.getBody().get(0).getBookVersion());
    }

    @Test
    void findAllPaginated_ShouldReturnPagedReservations() {
        Page<Reservation> page = new PageImpl<>(reservations);
        when(reservationService.getAllReservations(any(PageRequest.class))).thenReturn(page);

        ResponseEntity<Page<Reservation>> response = reservationRest.findAllPaginated(0, 10, new String[]{"reservationDate", "asc"});
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(100L, response.getBody().getContent().get(0).getUserId());
    }

    @Test
    void create_ShouldReturnCreatedReservation() {
        when(reservationService.create(any(Reservation.class))).thenReturn(reservation);
        Reservation result = reservationRest.create(reservation).getBody();
        assertNotNull(result);
        assertEquals("978-99955-42-10-4", result.getBookVersion());
    }

    @Test
    void update_ShouldReturnUpdatedReservation() {
        reservation.setReservationDate(LocalDateTime.of(2024, 5, 9, 14, 0));
        when(reservationService.update(any(Reservation.class))).thenReturn(reservation);
        Reservation result = reservationRest.update(1L, reservation).getBody();
        assertNotNull(result);
        assertEquals("2024-05-09T14:00", result.getReservationDate().toString().substring(0, 16));
    }

    @Test
    void createBatch_ShouldReturnCreatedReservations() {
        when(reservationService.createBatch(anyList())).thenReturn(reservations);
        List<Reservation> result = reservationRest.createBatch(reservations).getBody();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).getUserId());
    }
}
