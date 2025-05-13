package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Author;
import ba.unsa.etf.book.api.service.AuthorService;
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
public class AuthorRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorService authorService;

    @Autowired
    ObjectMapper objectMapper;

    private Author author;
    private List<Author> authors;

    @BeforeEach
    void setUp() {
        author = new Author(1L, "Ivo", "Andrić", "Dobitnik Nobelove nagrade");
        authors = List.of(author);
    }

    @Test
    public void findAll_ShouldReturnListOfAuthors() throws Exception {
        when(authorService.findAll()).thenReturn(authors);

        mockMvc.perform(get("/author"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("Ivo"));

        verify(authorService).findAll();
    }

    @Test
    void findById_ShouldReturnAuthor() throws Exception {
        when(authorService.findById(1L)).thenReturn(author);

        mockMvc.perform(get("/author/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ivo"));
    }

    @Test
    void delete_ShouldReturnConfirmationMessage() throws Exception {
        doNothing().when(authorService).delete(1L);

        mockMvc.perform(delete("/author/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Author with ID 1 was deleted successfully."));
    }

    @Test
    void findByBiographyKeyword_ShouldReturnFilteredAuthors() throws Exception {
        when(authorService.findByBiographyKeyword("Nobel")).thenReturn(authors);

        mockMvc.perform(get("/author/search-by-biography")
                        .param("keyword", "Nobel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].biography").value("Dobitnik Nobelove nagrade"));
    }

    @Test
    void findByCriteria_ShouldReturnFilteredAuthors() throws Exception {
        when(authorService.searchAuthors("Ivo", null, null)).thenReturn(authors);

        mockMvc.perform(get("/author/search")
                        .param("firstName", "Ivo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Ivo"));
    }

    @Test
    void findAllPaginated_ShouldReturnPagedAuthors() throws Exception {
        Page<Author> page = new PageImpl<>(authors);
        when(authorService.getAllAuthors(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/author/paginated")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "lastName", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Ivo"));
    }

    @Test
    void create_ShouldReturnCreatedAuthor() throws Exception {
        when(authorService.create(any(Author.class))).thenReturn(author);

        mockMvc.perform(post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ivo"));
    }

    @Test
    void update_ShouldReturnUpdatedAuthor() throws Exception {
        author.setBiography("Nova biografija");

        when(authorService.update(any(Author.class))).thenReturn(author);

        mockMvc.perform(put("/author/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.biography").value("Nova biografija"));
    }

    @Test
    void createBatch_ShouldReturnCreatedAuthors() throws Exception {
        when(authorService.createBatch(anyList())).thenReturn(authors);

        mockMvc.perform(post("/author/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authors)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Ivo"));
    }
}
