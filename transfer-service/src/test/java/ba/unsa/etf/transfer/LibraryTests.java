package ba.unsa.etf.transfer;

import ba.unsa.etf.transfer.api.model.Library;
import ba.unsa.etf.transfer.core.impl.LibraryServiceImpl;
import ba.unsa.etf.transfer.core.mapper.LibraryMapper;
import ba.unsa.etf.transfer.core.validation.LibraryValidation;
import ba.unsa.etf.transfer.dao.model.LibraryEntity;
import ba.unsa.etf.transfer.dao.repository.LibraryRepository;
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
class LibraryTests {
    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private LibraryMapper libraryMapper;

    @Mock
    private LibraryValidation libraryValidation;

    @InjectMocks
    private LibraryServiceImpl libraryService;

    private Library library;
    private LibraryEntity libraryEntity;

    @BeforeEach
    void setUp() {
        library = new Library(1L, "City Library", "Main Street 123", "+38761123456");
        libraryEntity = new LibraryEntity(1L, "City Library", "Main Street 123", "+38761123456");
    }

    @Test
    void findAll_ShouldReturnAllLibraries() {
        when(libraryRepository.findAll()).thenReturn(List.of(libraryEntity));
        when(libraryMapper.entityToDto(libraryEntity)).thenReturn(library);

        List<Library> libraries = libraryService.findAll();

        assertEquals(1, libraries.size());
        assertEquals("City Library", libraries.get(0).getName());
        verify(libraryRepository, times(1)).findAll();
    }

    @Test
    void findById_ShouldReturnLibrary_WhenIdExists() {
        when(libraryRepository.findById(1L)).thenReturn(Optional.of(libraryEntity));
        when(libraryMapper.entityToDto(libraryEntity)).thenReturn(library);

        Library foundLibrary = libraryService.findById(1L);

        assertNotNull(foundLibrary);
        assertEquals("City Library", foundLibrary.getName());
        verify(libraryValidation, times(1)).exists(1L);
    }

    @Test
    void findById_ShouldReturnNull_WhenIdDoesNotExist() {
        when(libraryRepository.findById(2L)).thenReturn(Optional.empty());

        Library foundLibrary = libraryService.findById(2L);

        assertNull(foundLibrary);
        verify(libraryValidation, times(1)).exists(2L);
    }

    @Test
    void create_ShouldSaveAndReturnLibrary() {
        when(libraryMapper.dtoToEntity(library)).thenReturn(libraryEntity);
        when(libraryRepository.save(libraryEntity)).thenReturn(libraryEntity);
        when(libraryMapper.entityToDto(libraryEntity)).thenReturn(library);

        Library createdLibrary = libraryService.create(library);

        assertNotNull(createdLibrary);
        assertEquals("City Library", createdLibrary.getName());
        verify(libraryRepository, times(1)).save(libraryEntity);
    }

    @Test
    void update_ShouldSaveAndReturnLibrary_WhenIdExists() {
        when(libraryRepository.existsById(1L)).thenReturn(true);
        when(libraryMapper.dtoToEntity(library)).thenReturn(libraryEntity);
        when(libraryRepository.save(libraryEntity)).thenReturn(libraryEntity);
        when(libraryMapper.entityToDto(libraryEntity)).thenReturn(library);

        Library updatedLibrary = libraryService.update(library);

        assertNotNull(updatedLibrary);
        assertEquals("City Library", updatedLibrary.getName());
        verify(libraryRepository, times(1)).save(libraryEntity);
    }

    @Test
    void update_ShouldReturnNull_WhenIdDoesNotExist() {
        when(libraryRepository.existsById(2L)).thenReturn(false);

        Library updatedLibrary = libraryService.update(new Library(2L, "New Library", "Street 456", "+38761123457"));

        assertNull(updatedLibrary);
        verify(libraryRepository, never()).save(any());
    }

    @Test
    void delete_ShouldRemoveLibrary_WhenIdExists() {
        doNothing().when(libraryValidation).exists(1L);
        doNothing().when(libraryRepository).deleteById(1L);

        libraryService.delete(1L);

        verify(libraryValidation, times(1)).exists(1L);
        verify(libraryRepository, times(1)).deleteById(1L);
    }
}
