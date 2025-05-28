package ba.unsa.etf.transfer.dao.model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transfer")
public class TransferEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_isbn")
    private String bookVersion;

    @Column(name = "library_from")
    private Long libraryFrom;

    @Column(name = "library_to")
    private Long libraryTo;

    @Column(name = "transfer_date")
    private LocalDate transferDate;
}