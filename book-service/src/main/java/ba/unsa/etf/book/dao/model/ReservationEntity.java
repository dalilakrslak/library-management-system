package ba.unsa.etf.book.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation")
public class ReservationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "book_isbn")
    private BookVersionEntity bookVersion;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;
}
