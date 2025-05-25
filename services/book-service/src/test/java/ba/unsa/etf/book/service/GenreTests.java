package ba.unsa.etf.book.service;

import ba.unsa.etf.book.api.model.Genre;
import ba.unsa.etf.book.core.impl.GenreServiceImpl;
import ba.unsa.etf.book.core.mapper.GenreMapper;
import ba.unsa.etf.book.core.validation.GenreValidation;
import ba.unsa.etf.book.dao.model.GenreEntity;
import ba.unsa.etf.book.dao.repository.GenreRepository;
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
public class GenreTests {
    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreMapper genreMapper;

    @Mock
    private GenreValidation genreValidation;

    @InjectMocks
    private GenreServiceImpl genreService;

    private GenreEntity genreEntity;
    private Genre genreDto;

    @BeforeEach
    void setUp() {
        genreEntity = new GenreEntity(1L, "Fiction");
        genreDto = new Genre(1L, "Fiction");
        genreDto.setId(1L);
        genreDto.setName("Fiction");
    }

    @Test
    void testFindAll() {
        List<GenreEntity> genreEntities = List.of(
                new GenreEntity(1L, "Fiction"),
                new GenreEntity(2L, "Non-Fiction")
        );

        List<Genre> genreDtos = List.of(
                new Genre(1L, "Fiction"),
                new Genre(2L, "Non-Fiction")
        );

        when(genreRepository.findAll()).thenReturn(genreEntities);
        when(genreMapper.entityToDto(genreEntities.get(0))).thenReturn(genreDtos.get(0));
        when(genreMapper.entityToDto(genreEntities.get(1))).thenReturn(genreDtos.get(1));

        List<Genre> result = genreService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Fiction", result.get(0).getName());
        assertEquals("Non-Fiction", result.get(1).getName());

        verify(genreRepository).findAll();
        verify(genreMapper, times(2)).entityToDto(any(GenreEntity.class));
    }

    @Test
    void testFindById_GenreExists() {
        Long id = 1L;
        when(genreRepository.findById(id)).thenReturn(Optional.of(genreEntity));
        when(genreMapper.entityToDto(genreEntity)).thenReturn(genreDto);

        Genre result = genreService.findById(id);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Fiction", result.getName());

        verify(genreValidation).exists(id);
        verify(genreRepository).findById(id);
        verify(genreMapper).entityToDto(genreEntity);
    }

    @Test
    void testFindById_GenreNotFound() {
        Long id = 2L;
        when(genreRepository.findById(id)).thenReturn(Optional.empty());

        Genre result = genreService.findById(id);

        assertNull(result);
        verify(genreValidation).exists(id);
        verify(genreRepository).findById(id);
        verifyNoMoreInteractions(genreMapper);
    }

    @Test
    void testCreate() {
        Genre genreToCreate = new Genre(1L, "Mystery");
        GenreEntity genreEntity = new GenreEntity(1L, "Mystery");
        GenreEntity savedEntity = new GenreEntity(1L, "Mystery");
        Genre genreDto = new Genre(1L, "Mystery");

        when(genreMapper.dtoToEntity(genreToCreate)).thenReturn(genreEntity);
        when(genreRepository.save(genreEntity)).thenReturn(savedEntity);
        when(genreMapper.entityToDto(savedEntity)).thenReturn(genreDto);

        Genre result = genreService.create(genreToCreate);

        assertNotNull(result);
        assertEquals(genreDto.getId(), result.getId());
        assertEquals(genreDto.getName(), result.getName());

        verify(genreRepository).save(genreEntity);
        verify(genreMapper).dtoToEntity(genreToCreate);
        verify(genreMapper).entityToDto(savedEntity);
    }

    @Test
    void testUpdate_GenreExists() {
        Genre genreToUpdate = new Genre(1L, "Science Fiction");
        GenreEntity genreEntity = new GenreEntity(1L, "Science Fiction");
        GenreEntity updatedEntity = new GenreEntity(1L, "Science Fiction");
        Genre genreDto = new Genre(1L, "Science Fiction");

        when(genreMapper.dtoToEntity(genreToUpdate)).thenReturn(genreEntity);
        when(genreRepository.save(genreEntity)).thenReturn(updatedEntity);
        when(genreMapper.entityToDto(updatedEntity)).thenReturn(genreDto);

        Genre result = genreService.update(genreToUpdate);

        assertNotNull(result);
        assertEquals(genreDto.getId(), result.getId());
        assertEquals(genreDto.getName(), result.getName());

        verify(genreRepository).save(genreEntity);
        verify(genreMapper).dtoToEntity(genreToUpdate);
        verify(genreMapper).entityToDto(updatedEntity);
    }

    @Test
    void testDelete() {
        Long id = 1L;
        doNothing().when(genreRepository).deleteById(id);

        genreService.delete(id);

        verify(genreValidation).exists(id);
        verify(genreRepository).deleteById(id);
    }
}
