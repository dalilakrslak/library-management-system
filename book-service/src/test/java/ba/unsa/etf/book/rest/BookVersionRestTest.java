package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.BookVersion;
import ba.unsa.etf.book.api.service.BookVersionService;
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
public class BookVersionRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookVersionService bookVersionService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookVersion bookVersion;
    private List<BookVersion> bookVersions;

    @BeforeEach
    void setUp() {
        bookVersion = new BookVersion();
        bookVersion.setIsbn("978-99955-42-10-4");
        bookVersion.setIsCheckedOut(false);
        bookVersion.setIsReserved(false);
        bookVersion.setBookId(1L);

        bookVersions = List.of(bookVersion);
    }

    @Test
    public void findAll_ShouldReturnListOfBookVersions() throws Exception {
        when(bookVersionService.findAll()).thenReturn(bookVersions);

        mockMvc.perform(get("/book-version"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].isbn").value("978-99955-42-10-4"));
    }

    @Test
    void findById_ShouldReturnBookVersion() throws Exception {
        when(bookVersionService.findById("978-99955-42-10-4")).thenReturn(bookVersion);

        mockMvc.perform(get("/book-version/{isbn}", "978-99955-42-10-4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId").value(1L));
    }

    @Test
    void delete_ShouldReturnConfirmationMessage() throws Exception {
        doNothing().when(bookVersionService).delete("978-99955-42-10-4");

        mockMvc.perform(delete("/book-version/{isbn}", "978-99955-42-10-4"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book version with ISBN 978-99955-42-10-4 was deleted successfully."));
    }

    @Test
    void findBooksByTitle_ShouldReturnFilteredBooks() throws Exception {
        when(bookVersionService.findBooksByTitle("Na Drini ćuprija")).thenReturn(bookVersions);

        mockMvc.perform(get("/book-version/search")
                        .param("title", "Na Drini ćuprija"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value("978-99955-42-10-4"));
    }

    @Test
    void findAllPaginated_ShouldReturnPagedBooks() throws Exception {
        Page<BookVersion> page = new PageImpl<>(bookVersions);
        when(bookVersionService.getAllBooks(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/book-version/paginated")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "bookId", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].isbn").value("978-99955-42-10-4"));
    }

    @Test
    void create_ShouldReturnCreatedBookVersion() throws Exception {
        when(bookVersionService.create(any(BookVersion.class))).thenReturn(bookVersion);

        mockMvc.perform(post("/book-version")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookVersion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("978-99955-42-10-4"));
    }

    @Test
    void update_ShouldReturnUpdatedBookVersion() throws Exception {
        bookVersion.setIsReserved(true);
        when(bookVersionService.update(any(BookVersion.class))).thenReturn(bookVersion);

        mockMvc.perform(put("/book-version/{isbn}", "978-99955-42-10-4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookVersion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isReserved").value(true));
    }

    @Test
    void createBatch_ShouldReturnCreatedBookVersions() throws Exception {
        when(bookVersionService.createBatch(anyList())).thenReturn(bookVersions);

        mockMvc.perform(post("/book-version/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookVersions)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value("978-99955-42-10-4"));
    }
}
