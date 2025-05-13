package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Genre;
import ba.unsa.etf.book.api.service.GenreService;
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
public class GenreRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GenreService genreService;

    @Autowired
    private ObjectMapper objectMapper;

    private Genre genre;
    private List<Genre> genres;

    @BeforeEach
    void setUp() {
        genre = new Genre(1L, "Historical");
        genres = List.of(genre);
    }

    @Test
    void findAll_ShouldReturnListOfGenres() throws Exception {
        when(genreService.findAll()).thenReturn(genres);

        mockMvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Historical"));
    }

    @Test
    void findById_ShouldReturnGenre() throws Exception {
        when(genreService.findById(1L)).thenReturn(genre);

        mockMvc.perform(get("/genre/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Historical"));
    }

    @Test
    void delete_ShouldReturnConfirmationMessage() throws Exception {
        doNothing().when(genreService).delete(1L);

        mockMvc.perform(delete("/genre/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Genre with ID 1 was deleted successfully."));
    }

    @Test
    void findByName_ShouldReturnGenres() throws Exception {
        when(genreService.findByName("Historical")).thenReturn(genres);

        mockMvc.perform(get("/genre/search")
                        .param("name", "Historical"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Historical"));
    }

    @Test
    void findAllPaginated_ShouldReturnPagedGenres() throws Exception {
        Page<Genre> page = new PageImpl<>(genres);
        when(genreService.getAllGenres(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/genre/paginated")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "name", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Historical"));
    }

    @Test
    void create_ShouldReturnCreatedGenre() throws Exception {
        when(genreService.create(any(Genre.class))).thenReturn(genre);

        mockMvc.perform(post("/genre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Historical"));
    }

    @Test
    void update_ShouldReturnUpdatedGenre() throws Exception {
        genre.setName("Biography");
        when(genreService.update(any(Genre.class))).thenReturn(genre);

        mockMvc.perform(put("/genre/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Biography"));
    }

    @Test
    void createBatch_ShouldReturnCreatedGenres() throws Exception {
        when(genreService.createBatch(anyList())).thenReturn(genres);

        mockMvc.perform(post("/genre/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genres)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Historical"));
    }
}
