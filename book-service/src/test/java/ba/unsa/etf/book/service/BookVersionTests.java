package ba.unsa.etf.book.service;

import ba.unsa.etf.book.api.model.BookVersion;
import ba.unsa.etf.book.core.impl.BookVersionServiceImpl;
import ba.unsa.etf.book.core.mapper.BookVersionMapper;
import ba.unsa.etf.book.core.validation.BookVersionValidation;
import ba.unsa.etf.book.dao.model.BookEntity;
import ba.unsa.etf.book.dao.model.BookVersionEntity;
import ba.unsa.etf.book.dao.repository.BookVersionRepository;
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
public class BookVersionTests {
    @Mock
    private BookVersionRepository bookVersionRepository;

    @Mock
    private BookVersionMapper bookVersionMapper;

    @Mock
    private BookVersionValidation bookVersionValidation;

    @InjectMocks
    private BookVersionServiceImpl bookVersionService;

    private BookEntity bookEntity;
    private BookVersionEntity bookVersionEntity;
    private BookVersion bookVersion;

    @BeforeEach
    void setUp() {
        bookEntity = new BookEntity(1L, "Title", "Description", 300, 2022, "English", null, null);
        bookVersionEntity = new BookVersionEntity("123-456-789", bookEntity, false, false);
        bookVersion = new BookVersion();
        bookVersion.setIsbn("123-456-789");
        bookVersion.setIsCheckedOut(false);
        bookVersion.setIsReserved(false);
        bookVersion.setBookId(1L);
    }

    @Test
    void testCreate() {
        when(bookVersionMapper.dtoToEntity(any(BookVersion.class))).thenReturn(bookVersionEntity);
        when(bookVersionRepository.save(any(BookVersionEntity.class))).thenReturn(bookVersionEntity);
        when(bookVersionMapper.entityToDto(any(BookVersionEntity.class))).thenReturn(bookVersion);

        BookVersion result = bookVersionService.create(bookVersion);

        assertNotNull(result);
        assertEquals(bookVersion.getIsbn(), result.getIsbn());
        verify(bookVersionRepository).save(any(BookVersionEntity.class));
    }

    @Test
    void testFindByIsbn() {
        when(bookVersionRepository.findById("123-456-789")).thenReturn(Optional.of(bookVersionEntity));
        when(bookVersionMapper.entityToDto(any(BookVersionEntity.class))).thenReturn(bookVersion);

        BookVersion result = bookVersionService.findById("123-456-789");

        assertNotNull(result);
        assertEquals("123-456-789", result.getIsbn());
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
