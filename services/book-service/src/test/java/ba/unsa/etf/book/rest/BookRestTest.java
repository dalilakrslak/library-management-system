package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Book;
import ba.unsa.etf.book.api.service.BookService;
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

class BookRestTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookRest bookRest;

    private Book book;
    private List<Book> books;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book(1L, "Na Drini ćuprija", "Roman o mostu na Drini", 312, 1945, "Bosanski", 100L, 10L);
        books = List.of(book);
    }

    @Test
    void findAll_ShouldReturnListOfBooks() {
        when(bookService.findAll()).thenReturn(books);
        List<Book> result = bookRest.findAll();
        assertEquals(1, result.size());
        assertEquals("Na Drini ćuprija", result.get(0).getTitle());
    }

    @Test
    void findById_ShouldReturnBook() {
        when(bookService.findById(1L)).thenReturn(book);
        Book result = bookRest.findById(1L).getBody();
        assertNotNull(result);
        assertEquals("Na Drini ćuprija", result.getTitle());
    }

    @Test
    void delete_ShouldReturnConfirmation() {
        doNothing().when(bookService).delete(1L);
        var response = bookRest.delete(1L);
        verify(bookService).delete(1L);
        assertEquals("Book with ID 1 was deleted successfully.", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void findBooksByAuthor_ShouldReturnBooks() {
        when(bookService.findBooksByAuthor("Andrić")).thenReturn(books);
        ResponseEntity<List<Book>> result = bookRest.findBooksByAuthor("Andrić");
        assertEquals(1, result.getBody().size());
        assertEquals("Na Drini ćuprija", result.getBody().get(0).getTitle());
    }

    @Test
    void findAllPaginated_ShouldReturnPagedBooks() {
        Page<Book> page = new PageImpl<>(books);
        when(bookService.getAllBooks(any(PageRequest.class))).thenReturn(page);
        ResponseEntity<Page<Book>> response = bookRest.findAllPaginated(0, 10, new String[]{"title", "asc"});
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals("Na Drini ćuprija", response.getBody().getContent().get(0).getTitle());
    }

    @Test
    void create_ShouldReturnCreatedBook() {
        when(bookService.create(any(Book.class))).thenReturn(book);
        Book result = bookRest.create(book).getBody();
        assertNotNull(result);
        assertEquals("Na Drini ćuprija", result.getTitle());
    }

    @Test
    void update_ShouldReturnUpdatedBook() {
        book.setDescription("Novo izdanje sa dodatkom");
        when(bookService.update(any(Book.class))).thenReturn(book);
        Book result = bookRest.update(1L, book).getBody();
        assertNotNull(result);
        assertEquals("Novo izdanje sa dodatkom", result.getDescription());
    }

    @Test
    void createBatch_ShouldReturnCreatedBooks() {
        when(bookService.createBatch(anyList())).thenReturn(books);
        List<Book> result = bookRest.createBatch(books).getBody();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Na Drini ćuprija", result.get(0).getTitle());
    }
}
