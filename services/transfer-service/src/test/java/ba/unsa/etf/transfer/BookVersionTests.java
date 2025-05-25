package ba.unsa.etf.transfer;

import ba.unsa.etf.transfer.api.model.BookVersion;
import ba.unsa.etf.transfer.core.impl.BookVersionServiceImpl;
import ba.unsa.etf.transfer.core.mapper.BookVersionMapper;
import ba.unsa.etf.transfer.core.validation.BookVersionValidation;
import ba.unsa.etf.transfer.dao.model.BookVersionEntity;
import ba.unsa.etf.transfer.dao.repository.BookVersionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookVersionTests {

    @Mock
    private BookVersionRepository bookVersionRepository;

    @Mock
    private BookVersionMapper bookVersionMapper;

    @Mock
    private BookVersionValidation bookVersionValidation;

    @InjectMocks
    private BookVersionServiceImpl bookVersionService;

    private BookVersionEntity bookVersionEntity;
    private BookVersion bookVersionDto;

    @BeforeEach
    void setUp() {
        bookVersionEntity = new BookVersionEntity("123-456-789", null, null, true, false);
        bookVersionDto = new BookVersion("123-456-789", null, null, true, false);
    }

    @Test
    void testFindByIsbn_BookVersionExists() {
        String isbn = "123-456-789";
        when(bookVersionRepository.findById(isbn)).thenReturn(Optional.of(bookVersionEntity));
        when(bookVersionMapper.entityToDto(bookVersionEntity)).thenReturn(bookVersionDto);

        BookVersion result = bookVersionService.findById(isbn);

        assertNotNull(result);
        assertEquals(bookVersionDto.getIsbn(), result.getIsbn());

        verify(bookVersionValidation).exists(isbn);
        verify(bookVersionRepository).findById(isbn);
        verify(bookVersionMapper).entityToDto(bookVersionEntity);
    }

    @Test
    void testFindByIsbn_BookVersionNotFound() {
        String isbn = "987-654-321";
        when(bookVersionRepository.findById(isbn)).thenReturn(Optional.empty());

        BookVersion result = bookVersionService.findById(isbn);

        assertNull(result);
        verify(bookVersionValidation).exists(isbn);
        verify(bookVersionRepository).findById(isbn);
        verifyNoMoreInteractions(bookVersionMapper);
    }

    @Test
    void testDelete() {
        String isbn = "123-456-789";

        doNothing().when(bookVersionRepository).deleteById(isbn);

        bookVersionService.delete(isbn);

        verify(bookVersionValidation).exists(isbn);
        verify(bookVersionRepository).deleteById(isbn);
    }
}
