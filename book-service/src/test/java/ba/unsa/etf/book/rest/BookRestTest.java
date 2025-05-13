package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Book;
import ba.unsa.etf.book.api.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book;
    private List<Book> books;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Na Drini ćuprija", "Roman o mostu na Drini", 312, 1945, "Bosanski", 100L, 10L);
        books = List.of(book);
    }

    @Test
    public void findAll_ShouldReturnListOfBooks() throws Exception {
        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Na Drini ćuprija"));

        verify(bookService).findAll();
    }

    @Test
    void findById_ShouldReturnBook() throws Exception {
        when(bookService.findById(1L)).thenReturn(book);

        mockMvc.perform(get("/book/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Na Drini ćuprija"));
    }

    @Test
    void delete_ShouldReturnConfirmationMessage() throws Exception {
        doNothing().when(bookService).delete(1L);

        mockMvc.perform(delete("/book/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Book with ID 1 was deleted successfully."));
    }

    @Test
    void findBooksByAuthor_ShouldReturnBooks() throws Exception {
        when(bookService.findBooksByAuthor("Andrić")).thenReturn(books);

        mockMvc.perform(get("/book/search")
                        .param("authorName", "Andrić"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Na Drini ćuprija"));
    }

    @Test
    void findAllPaginated_ShouldReturnPagedBooks() throws Exception {
        Page<Book> page = new PageImpl<>(books);
        when(bookService.getAllBooks(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/book/paginated")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "title", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Na Drini ćuprija"));
    }

    @Test
    void create_ShouldReturnCreatedBook() throws Exception {
        when(bookService.create(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Na Drini ćuprija"));
    }

    @Test
    void update_ShouldReturnUpdatedBook() throws Exception {
        book.setDescription("Novo izdanje sa dodatkom");

        when(bookService.update(any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/book/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Novo izdanje sa dodatkom"));
    }

    @Test
    void createBatch_ShouldReturnCreatedBooks() throws Exception {
        when(bookService.createBatch(anyList())).thenReturn(books);

        mockMvc.perform(post("/book/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(books)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Na Drini ćuprija"));
    }
}