package ba.unsa.etf.library.dao.model;

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
    @JoinColumn(name = "library_id")
    private LibraryEntity library;

    private Boolean returned;

    private Boolean reserved;
}
