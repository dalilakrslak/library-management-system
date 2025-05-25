package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.BookVersion;
import ba.unsa.etf.book.api.service.BookVersionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class BookVersionRestTest {

    @Mock
    private BookVersionService bookVersionService;

    @InjectMocks
    private BookVersionRest bookVersionRest;

    private BookVersion bookVersion;
    private List<BookVersion> bookVersions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookVersion = new BookVersion();
        bookVersion.setIsbn("978-99955-42-10-4");
        bookVersion.setIsCheckedOut(false);
        bookVersion.setIsReserved(false);
        bookVersion.setBookId(1L);

        bookVersions = List.of(bookVersion);
    }

    @Test
    void findAll_ShouldReturnListOfBookVersions() {
        when(bookVersionService.findAll()).thenReturn(bookVersions);
        List<BookVersion> result = bookVersionRest.findAll();
        assertEquals(1, result.size());
        assertEquals("978-99955-42-10-4", result.get(0).getIsbn());
    }

    @Test
    void findById_ShouldReturnBookVersion() {
        when(bookVersionService.findById("978-99955-42-10-4")).thenReturn(bookVersion);
        var result = bookVersionRest.findById("978-99955-42-10-4").getBody();
        assertNotNull(result);
        assertEquals(1L, result.getBookId());
    }

    @Test
    void delete_ShouldReturnConfirmationMessage() {
        doNothing().when(bookVersionService).delete("978-99955-42-10-4");

        var response = bookVersionRest.delete("978-99955-42-10-4");
        verify(bookVersionService).delete("978-99955-42-10-4");
        assertEquals("Book version with ISBN 978-99955-42-10-4 was deleted successfully.", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void findBooksByTitle_ShouldReturnFilteredBooks() {
        when(bookVersionService.findBooksByTitle("Na Drini ćuprija")).thenReturn(bookVersions);
        ResponseEntity<List<BookVersion>> result = bookVersionRest.findBooksByTitle("Na Drini ćuprija");
        assertEquals(1, result.getBody().size());
        assertEquals("978-99955-42-10-4", result.getBody().get(0).getIsbn());
    }

    @Test
    void findAllPaginated_ShouldReturnPagedBooks() {
        Page<BookVersion> page = new PageImpl<>(bookVersions);
        when(bookVersionService.getAllBooks(any(PageRequest.class))).thenReturn(page);

        ResponseEntity<Page<BookVersion>> response = bookVersionRest.findAllPaginated(0, 10, new String[]{"bookId", "asc"});
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals("978-99955-42-10-4", response.getBody().getContent().get(0).getIsbn());
    }

    @Test
    void create_ShouldReturnCreatedBookVersion() {
        when(bookVersionService.create(any(BookVersion.class))).thenReturn(bookVersion);
        var result = bookVersionRest.create(bookVersion).getBody();
        assertNotNull(result);
        assertEquals("978-99955-42-10-4", result.getIsbn());
    }

    @Test
    void update_ShouldReturnUpdatedBookVersion() {
        bookVersion.setIsReserved(true);
        when(bookVersionService.update(any(BookVersion.class))).thenReturn(bookVersion);
        var result = bookVersionRest.update("978-99955-42-10-4", bookVersion).getBody();
        assertNotNull(result);
        assertTrue(result.getIsReserved());
    }

    @Test
    void createBatch_ShouldReturnCreatedBookVersions() {
        when(bookVersionService.createBatch(anyList())).thenReturn(bookVersions);
        var result = bookVersionRest.createBatch(bookVersions).getBody();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("978-99955-42-10-4", result.get(0).getIsbn());
    }
}
