package ba.unsa.etf.transfer;

import ba.unsa.etf.transfer.api.model.Book;
import ba.unsa.etf.transfer.core.exception.ValidationException;
import ba.unsa.etf.transfer.core.impl.BookServiceImpl;
import ba.unsa.etf.transfer.core.mapper.BookMapper;
import ba.unsa.etf.transfer.core.validation.BookValidation;
import ba.unsa.etf.transfer.dao.model.AuthorEntity;
import ba.unsa.etf.transfer.dao.model.BookEntity;
import ba.unsa.etf.transfer.dao.model.GenreEntity;
import ba.unsa.etf.transfer.dao.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookTests {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookValidation bookValidation;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookEntity bookEntity;
    private Book bookDto;

    @BeforeEach
    void setUp() {
        AuthorEntity author = new AuthorEntity(1L, "John", "Doe", "Biography");
        GenreEntity genre = new GenreEntity(1L, "Fiction");

        bookEntity = new BookEntity(1L, "Book Title", "Book Description", 300, "2023", "English", author, genre);
        bookDto = new Book(1L, "Book Title", "Book Description", 300, 2023, "English", 1L, 1L);
    }

    @Test
    void testFindById_BookExists() {
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.of(bookEntity));
        when(bookMapper.entityToDto(bookEntity)).thenReturn(bookDto);

        Book result = bookService.findById(id);

        assertNotNull(result);
        assertEquals(bookDto.getId(), result.getId());
        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getDescription(), result.getDescription());

        verify(bookValidation).exists(id);
        verify(bookRepository).findById(id);
        verify(bookMapper).entityToDto(bookEntity);
    }

    @Test
    void testFindById_BookNotFound() {
        Long id = 2L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        Book result = bookService.findById(id);

        assertNull(result);
        verify(bookValidation).exists(id);
        verify(bookRepository).findById(id);
        verifyNoMoreInteractions(bookMapper);
    }

    @Test
    void testDelete_BookExists() {
        Long id = 1L;
        doNothing().when(bookValidation).exists(id);
        doNothing().when(bookRepository).deleteById(id);

        bookService.delete(id);

        verify(bookValidation).exists(id);
        verify(bookRepository).deleteById(id);
    }

    @Test
    void testDelete_BookNotFound() {
        Long id = 2L;
        doThrow(new ValidationException("Book with given ID does not exist!"))
                .when(bookValidation).exists(id);

        assertThrows(ValidationException.class, () -> bookService.delete(id));

        verify(bookValidation).exists(id);
        verifyNoInteractions(bookRepository);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        doNothing().when(bookRepository).deleteById(id);

        bookService.delete(id);

        verify(bookValidation).exists(id);
        verify(bookRepository).deleteById(id);
    }
}
