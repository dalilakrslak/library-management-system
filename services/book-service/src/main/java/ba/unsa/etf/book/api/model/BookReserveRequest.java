package ba.unsa.etf.book.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReserveRequest {
    private String isbn;
    private Long libraryFrom;
    private Long libraryTo;
}