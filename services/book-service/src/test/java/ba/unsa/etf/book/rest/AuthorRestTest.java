package ba.unsa.etf.book.rest;

import ba.unsa.etf.book.api.model.Author;
import ba.unsa.etf.book.api.service.AuthorService;
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

class AuthorRestTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorRest authorRest;

    private Author author;
    private List<Author> authors;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author(1L, "Ivo", "Andrić", "Dobitnik Nobelove nagrade");
        authors = List.of(author);
    }

    @Test
    void findAll_ShouldReturnList() {
        when(authorService.findAll()).thenReturn(authors);
        List<Author> result = authorRest.findAll();
        assertEquals(1, result.size());
        assertEquals("Ivo", result.get(0).getFirstName());
    }

    @Test
    void findById_ShouldReturnAuthor() {
        when(authorService.findById(1L)).thenReturn(author);
        Author result = authorRest.findById(1L).getBody();
        assertEquals("Ivo", result != null ? result.getFirstName() : null);
    }

    @Test
    void delete_ShouldCallService() {
        doNothing().when(authorService).delete(1L);
        var response = authorRest.delete(1L);
        verify(authorService).delete(1L);
        assertEquals("Author with ID 1 was deleted successfully.", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void findByBiographyKeyword_ShouldReturnFiltered() {
        when(authorService.findByBiographyKeyword("Nobel")).thenReturn(authors);
        ResponseEntity<List<Author>> result = authorRest.findByBiographyKeyword("Nobel");
        assertEquals(1, result.getBody().size());
        assertEquals("Dobitnik Nobelove nagrade", result.getBody().get(0).getBiography());
    }

    @Test
    void findByCriteria_ShouldReturnFiltered() {
        when(authorService.searchAuthors("Ivo", null, null)).thenReturn(authors);
        List<Author> result = authorRest.findByCriteria("Ivo", null, null);
        assertEquals(1, result.size());
        assertEquals("Ivo", result.get(0).getFirstName());
    }

    @Test
    void findAllPaginated_ShouldReturnPaged() {
        Page<Author> page = new PageImpl<>(authors);
        when(authorService.getAllAuthors(any(PageRequest.class))).thenReturn(page);

        ResponseEntity<Page<Author>> response = authorRest.findAllPaginated(0, 10, new String[]{"lastName", "asc"});
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
    }

    @Test
    void create_ShouldReturnCreatedAuthor() {
        when(authorService.create(any(Author.class))).thenReturn(author);
        Author result = authorRest.create(author).getBody();
        assertNotNull(result);
        assertEquals("Ivo", result.getFirstName());
    }

    @Test
    void update_ShouldReturnUpdatedAuthor() {
        author.setBiography("Nova biografija");
        when(authorService.update(any(Author.class))).thenReturn(author);
        Author result = authorRest.update(1L, author).getBody();
        assertNotNull(result);
        assertEquals("Nova biografija", result.getBiography());
    }

    @Test
    void createBatch_ShouldReturnCreatedAuthors() {
        when(authorService.createBatch(anyList())).thenReturn(authors);
        List<Author> result = authorRest.createBatch(authors).getBody();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
