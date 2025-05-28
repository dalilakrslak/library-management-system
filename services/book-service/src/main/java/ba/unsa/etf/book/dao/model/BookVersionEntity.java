package ba.unsa.etf.book.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_version")
public class BookVersionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @Column(name = "is_checked_out")
    private Boolean isCheckedOut;

    @Column(name = "is_reserved")
    private Boolean isReserved;

    @Column(name = "library_id")
    private Long libraryId;
}
