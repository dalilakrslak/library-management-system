package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Reservation;
import ba.unsa.etf.book.api.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    private Reservation reservation;
    private List<Reservation> reservations;

    @BeforeEach
    void setUp() {
        reservation = new Reservation(
                1L,
                100L,
                "978-99955-42-10-4",
                LocalDateTime.of(2024, 5, 8, 12, 0)
        );

        reservations = List.of(reservation);
    }

    @Test
    void findAll_ShouldReturnListOfReservations() throws Exception {
        when(reservationService.findAll()).thenReturn(reservations);

        mockMvc.perform(get("/reservation"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userId").value(100));
    }

    @Test
    void findById_ShouldReturnReservation() throws Exception {
        when(reservationService.findById(1L)).thenReturn(reservation);

        mockMvc.perform(get("/reservation/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookVersion").value("978-99955-42-10-4"));
    }

    @Test
    void delete_ShouldReturnConfirmationMessage() throws Exception {
        doNothing().when(reservationService).delete(1L);

        mockMvc.perform(delete("/reservation/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Reservation with ID 1 was deleted successfully."));
    }

    @Test
    void findReservationsByUserId_ShouldReturnUserReservations() throws Exception {
        when(reservationService.findReservationsByUserId(100L)).thenReturn(reservations);

        mockMvc.perform(get("/reservation/search")
                        .param("userId", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookVersion").value("978-99955-42-10-4"));
    }

    @Test
    void findAllPaginated_ShouldReturnPagedReservations() throws Exception {
        Page<Reservation> page = new PageImpl<>(reservations);
        when(reservationService.getAllReservations(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/reservation/paginated")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "reservationDate", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].userId").value(100));
    }

    @Test
    void create_ShouldReturnCreatedReservation() throws Exception {
        when(reservationService.create(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(post("/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookVersion").value("978-99955-42-10-4"));
    }

    @Test
    void update_ShouldReturnUpdatedReservation() throws Exception {
        reservation.setReservationDate(LocalDateTime.of(2024, 5, 9, 14, 0));
        when(reservationService.update(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(put("/reservation/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationDate").value("2024-05-09T14:00:00"));
    }

    @Test
    void createBatch_ShouldReturnCreatedReservations() throws Exception {
        when(reservationService.createBatch(anyList())).thenReturn(reservations);

        mockMvc.perform(post("/reservation/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservations)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(100));
    }
}
