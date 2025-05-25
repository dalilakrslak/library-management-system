package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Genre;
import ba.unsa.etf.book.api.service.GenreService;
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

class GenreRestTest {

    @Mock
    private GenreService genreService;

    @InjectMocks
    private GenreRest genreRest;

    private Genre genre;
    private List<Genre> genres;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        genre = new Genre(1L, "Historical");
        genres = List.of(genre);
    }

    @Test
    void findAll_ShouldReturnListOfGenres() {
        when(genreService.findAll()).thenReturn(genres);
        List<Genre> result = genreRest.findAll();
        assertEquals(1, result.size());
        assertEquals("Historical", result.get(0).getName());
    }

    @Test
    void findById_ShouldReturnGenre() {
        when(genreService.findById(1L)).thenReturn(genre);
        Genre result = genreRest.findById(1L).getBody();
        assertNotNull(result);
        assertEquals("Historical", result.getName());
    }

    @Test
    void delete_ShouldReturnConfirmationMessage() {
        doNothing().when(genreService).delete(1L);
        var response = genreRest.delete(1L);
        verify(genreService).delete(1L);
        assertEquals("Genre with ID 1 was deleted successfully.", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void findByName_ShouldReturnGenres() {
        when(genreService.findByName("Historical")).thenReturn(genres);
        ResponseEntity<List<Genre>> result = genreRest.findByName("Historical");
        assertEquals(1, result.getBody().size());
        assertEquals("Historical", result.getBody().get(0).getName());
    }

    @Test
    void findAllPaginated_ShouldReturnPagedGenres() {
        Page<Genre> page = new PageImpl<>(genres);
        when(genreService.getAllGenres(any(PageRequest.class))).thenReturn(page);

        ResponseEntity<Page<Genre>> response = genreRest.findAllPaginated(0, 10, new String[]{"name", "asc"});
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals("Historical", response.getBody().getContent().get(0).getName());
    }

    @Test
    void create_ShouldReturnCreatedGenre() {
        when(genreService.create(any(Genre.class))).thenReturn(genre);
        Genre result = genreRest.create(genre).getBody();
        assertNotNull(result);
        assertEquals("Historical", result.getName());
    }

    @Test
    void update_ShouldReturnUpdatedGenre() {
        genre.setName("Biography");
        when(genreService.update(any(Genre.class))).thenReturn(genre);
        Genre result = genreRest.update(1L, genre).getBody();
        assertNotNull(result);
        assertEquals("Biography", result.getName());
    }

    @Test
    void createBatch_ShouldReturnCreatedGenres() {
        when(genreService.createBatch(anyList())).thenReturn(genres);
        List<Genre> result = genreRest.createBatch(genres).getBody();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Historical", result.get(0).getName());
    }
}
