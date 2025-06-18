package ba.unsa.etf.transfer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTransferEvent {
    private Long transferId;
    private Long bookId;
    private Long userId;
    private String status;
}
