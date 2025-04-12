package ba.unsa.etf.book.repository;

import ba.unsa.etf.book.dao.model.BookVersionEntity;
import ba.unsa.etf.book.dao.model.ReservationEntity;
import ba.unsa.etf.book.dao.model.UserEntity;
import ba.unsa.etf.book.dao.repository.ReservationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReservationTests {

    @Autowired
    private ReservationRepository reservationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private Statistics statistics;

    @BeforeEach
    void setUp() {
        // Enable statistics to track queries
        SessionFactory sessionFactory = entityManager.unwrap(Session.class).getSessionFactory();
        statistics = sessionFactory.getStatistics();
        statistics.setStatisticsEnabled(true);

        // Set up test data
        UserEntity user = new UserEntity();
        user.setFirstName("John");
        user.setLastName("Doe");
        entityManager.persist(user);

        BookVersionEntity bookVersion = new BookVersionEntity();
        bookVersion.setIsbn("12345");
        bookVersion.setIsCheckedOut(false);
        bookVersion.setIsReserved(true);
        entityManager.persist(bookVersion);

        // Create and persist reservations
        for (int i = 0; i < 3; i++) {
            ReservationEntity reservation = new ReservationEntity();
            reservation.setUser(user);
            reservation.setBookVersion(bookVersion);
            reservation.setReservationDate(LocalDate.now());
            entityManager.persist(reservation);
        }

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void whenFetchingReservationsByUserId_NoNPlusOneProblem() {
        List<ReservationEntity> reservations = reservationRepository.findReservationsByUserId(1L);

        long queryCount = statistics.getQueryExecutionCount();

        // Expect only one query to be executed (no N+1 problem)
        assertEquals(1, queryCount, "There should be only one query executed!");
    }

    @Test
    void whenCallingFindAll_thenNoNPlusOneProblem() {
        List<ReservationEntity> reservations = reservationRepository.findAll();

        reservations.forEach(reservation -> {
            // Accessing user and bookVersion to simulate lazy loading
            reservation.getUser().getFirstName();
            reservation.getBookVersion().getIsbn();
        });

        long queryCount = statistics.getQueryExecutionCount();

        // Expect only one query to be executed (no N+1 problem)
        assertEquals(1, queryCount, "There should be only one query executed!");
    }
}
