package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Reservation;
import ba.unsa.etf.book.api.model.ReservationWithUser;
import ba.unsa.etf.book.api.model.User;
import ba.unsa.etf.book.api.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "reservation", description = "Reservation API")
@RestController
@RequestMapping(value = "/reservation")
@AllArgsConstructor
public class ReservationRest {
    private ReservationService reservationService;

    @Operation(summary = "Find reservations")
    @GetMapping
    public List<Reservation> findAll() {
        return reservationService.findAll();
    }

    @Operation(summary = "Find reservation by ID")
    @GetMapping(value = "{id}")
    public ResponseEntity<Reservation> findById(
            @Parameter(required = true, description = "ID of the reservation", name = "id") @PathVariable Long id) {
        Reservation reservation = reservationService.findById(id);
        return reservation != null ? ResponseEntity.ok(reservation) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create reservation")
    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation) {
        Reservation createdReservation = reservationService.create(reservation);
        return ResponseEntity.ok(createdReservation);
    }

    @Operation(summary = "Update reservation")
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> update(@PathVariable Long id, @RequestBody Reservation reservation) {
        reservation.setId(id);
        Reservation updatedReservation = reservationService.update(reservation);
        return updatedReservation != null ? ResponseEntity.ok(updatedReservation) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete reservation")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.ok("Reservation with ID " + id + " was deleted successfully.");
    }

    @Operation(summary = "Find reservations with pagination and sorting")
    @GetMapping("/paginated")
    public ResponseEntity<Page<Reservation>> findAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "reservationDate,asc") String[] sort
    ) {
        Sort sortOrder = Sort.by(
                sort[1].equalsIgnoreCase("desc") ? Sort.Order.desc(sort[0]) : Sort.Order.asc(sort[0])
        );
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Reservation> reservations = reservationService.getAllReservations(pageable);
        return ResponseEntity.ok(reservations);
    }

    @Operation(summary = "Batch create reservations")
    @PostMapping("/batch")
    public ResponseEntity<List<Reservation>> createBatch(@RequestBody List<Reservation> reservations) {
        List<Reservation> createdReservations = reservationService.createBatch(reservations);
        return ResponseEntity.ok(createdReservations);
    }

    @Operation(summary = "Find reservations by user ID")
    @GetMapping("/search")
    public ResponseEntity<List<Reservation>> findReservationsByUserId(
            @Parameter(description = "ID of the user") @RequestParam Long userId) {
        List<Reservation> reservations = reservationService.findReservationsByUserId(userId);
        return reservations.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(reservations);
    }

    @Operation(summary = "Find all reservations with user info")
    @GetMapping("/all-with-users")
    public ResponseEntity<List<ReservationWithUser>> getAllReservationsWithUserInfo() {
        List<ReservationWithUser> result = reservationService.getAllReservationsWithUserInfo();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get user info for reservation")
    @GetMapping("/user-by-reservation")
    public ResponseEntity<User> getUserByReservationId(@RequestParam Long reservationId) {
        User user = reservationService.getUserByReservationId(reservationId);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

}
