package ba.unsa.etf.book.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookWithVersions {
    private Long bookId;
    private String isbn;
    private String title;
    private String description;
    private int pageCount;
    private int publicationYear;
    private String language;
    private String authorFullName;
    private String genreName;
    private Long libraryId;
    private Boolean isCheckedOut;
    private Boolean isReserved;
}
