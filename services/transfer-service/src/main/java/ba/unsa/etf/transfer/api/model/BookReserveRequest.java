package ba.unsa.etf.transfer.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookReserveRequest {
    private String isbn;
    private Long libraryFrom;
    private Long libraryTo;
}