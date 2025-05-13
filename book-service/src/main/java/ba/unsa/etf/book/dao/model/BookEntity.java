package ba.unsa.etf.book.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class BookEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Column(name = "page_count")
    private int pageCount;

    @Column(name = "publication_year")
    private int publicationYear;

    private String language;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;
}
