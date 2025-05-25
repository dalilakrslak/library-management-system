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

    @ManyToOne
    @JoinColumn(name = "book_isbn")
    private BookVersionEntity bookVersion;

    @ManyToOne
    @JoinColumn(name = "library_from")
    private LibraryEntity libraryFrom;

    @ManyToOne
    @JoinColumn(name = "library_to")
    private LibraryEntity libraryTo;

    @Column(name = "transfer_date")
    private LocalDate transferDate;
}