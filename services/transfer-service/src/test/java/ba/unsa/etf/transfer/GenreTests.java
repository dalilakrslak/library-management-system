package ba.unsa.etf.transfer;

import ba.unsa.etf.transfer.api.model.Genre;
import ba.unsa.etf.transfer.core.exception.ValidationException;
import ba.unsa.etf.transfer.core.impl.GenreServiceImpl;
import ba.unsa.etf.transfer.core.mapper.GenreMapper;
import ba.unsa.etf.transfer.core.validation.GenreValidation;
import ba.unsa.etf.transfer.dao.model.GenreEntity;
import ba.unsa.etf.transfer.dao.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreTests {

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreMapper genreMapper;

    @Mock
    private GenreValidation genreValidation;

    @InjectMocks
    private GenreServiceImpl genreService;

    private GenreEntity genreEntity;
    private Genre genre;

    @BeforeEach
    void setUp() {
        genreEntity = new GenreEntity(1L, "Fiction");
        genre = new Genre(1L, "Fiction");
    }

    @Test
    void testFindAll() {
        when(genreRepository.findAll()).thenReturn(List.of(genreEntity));
        when(genreMapper.entityToDto(genreEntity)).thenReturn(genre);

        List<Genre> result = genreService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Fiction", result.get(0).getName());
    }

    @Test
    void testFindById_Success() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genreEntity));
        when(genreMapper.entityToDto(genreEntity)).thenReturn(genre);

        Genre result = genreService.findById(1L);

        assertNotNull(result);
        assertEquals("Fiction", result.getName());
    }

    @Test
    void testFindById_NotFound() {
        when(genreRepository.findById(1L)).thenReturn(Optional.empty());

        Genre result = genreService.findById(1L);

        assertNull(result);
    }

    @Test
    void testCreate() {
        when(genreMapper.dtoToEntity(genre)).thenReturn(genreEntity);
        when(genreRepository.save(genreEntity)).thenReturn(genreEntity);
        when(genreMapper.entityToDto(genreEntity)).thenReturn(genre);

        Genre result = genreService.create(genre);

        assertNotNull(result);
        assertEquals("Fiction", result.getName());
    }

    @Test
    void testUpdate_Success() {
        when(genreRepository.existsById(1L)).thenReturn(true);
        when(genreMapper.dtoToEntity(genre)).thenReturn(genreEntity);
        when(genreRepository.save(genreEntity)).thenReturn(genreEntity);
        when(genreMapper.entityToDto(genreEntity)).thenReturn(genre);

        Genre result = genreService.update(genre);

        assertNotNull(result);
        assertEquals("Fiction", result.getName());
    }

    @Test
    void testUpdate_NotFound() {
        when(genreRepository.existsById(1L)).thenReturn(false);

        Genre result = genreService.update(genre);

        assertNull(result);
    }

    @Test
    void testDelete_Success() {
        doNothing().when(genreValidation).exists(1L);
        doNothing().when(genreRepository).deleteById(1L);

        assertDoesNotThrow(() -> genreService.delete(1L));
    }

    @Test
    void testDelete_NotFound() {
        doThrow(new ValidationException("Genre with given ID does not exist!"))
                .when(genreValidation).exists(1L);

        assertThrows(ValidationException.class, () -> genreService.delete(1L));
    }
}
