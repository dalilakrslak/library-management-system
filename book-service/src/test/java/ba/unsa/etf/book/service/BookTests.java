package ba.unsa.etf.book.service;

import ba.unsa.etf.book.api.model.Book;
import ba.unsa.etf.book.core.impl.BookServiceImpl;
import ba.unsa.etf.book.core.mapper.BookMapper;
import ba.unsa.etf.book.core.validation.BookValidation;
import ba.unsa.etf.book.dao.model.AuthorEntity;
import ba.unsa.etf.book.dao.model.BookEntity;
import ba.unsa.etf.book.dao.model.GenreEntity;
import ba.unsa.etf.book.dao.repository.BookRepository;
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
class BookTests {

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
    private AuthorEntity authorEntity;
    private GenreEntity genreEntity;

    @BeforeEach
    void setUp() {
        authorEntity = new AuthorEntity(1L, "John", "Doe", "Biography");
        genreEntity = new GenreEntity(1L, "Fiction");
        bookEntity = new BookEntity(1L, "Book Title", "Description", 300, 2020, "English", authorEntity, genreEntity);
        bookDto = new Book(1L, "Book Title", "Description", 300, 2020, "English", 1L, 1L);
    }

    @Test
    void testFindAll() {
        List<BookEntity> bookEntities = List.of(bookEntity);
        List<Book> bookDtos = List.of(bookDto);

        when(bookRepository.findAll()).thenReturn(bookEntities);
        when(bookMapper.entityToDto(bookEntities.get(0))).thenReturn(bookDtos.get(0));

        List<Book> result = bookService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bookDtos.get(0).getId(), result.get(0).getId());

        verify(bookRepository).findAll();
        verify(bookMapper).entityToDto(any(BookEntity.class));
    }

    @Test
    void testFindById_BookExists() {
        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.of(bookEntity));
        when(bookMapper.entityToDto(bookEntity)).thenReturn(bookDto);

        Book result = bookService.findById(id);

        assertNotNull(result);
        assertEquals(bookDto.getId(), result.getId());

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
    void testCreate() {
        Book bookToCreate = new Book(1L, "New Book", "Description", 200, 2022, "French", 1L, 1L);
        BookEntity bookEntity = new BookEntity(1L, "New Book", "Description", 200, 2022, "French", authorEntity, genreEntity);
        BookEntity savedEntity = new BookEntity(1L, "New Book", "Description", 200, 2022, "French", authorEntity, genreEntity);
        Book bookDto = new Book(1L, "New Book", "Description", 200, 2022, "French", 1L, 1L);

        when(bookMapper.dtoToEntity(bookToCreate)).thenReturn(bookEntity);
        when(bookRepository.save(bookEntity)).thenReturn(savedEntity);
        when(bookMapper.entityToDto(savedEntity)).thenReturn(bookDto);

        Book result = bookService.create(bookToCreate);

        assertNotNull(result);
        assertEquals(bookDto.getId(), result.getId());
        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getDescription(), result.getDescription());
        assertEquals(bookDto.getPageCount(), result.getPageCount());
        assertEquals(bookDto.getPublicationYear(), result.getPublicationYear());
        assertEquals(bookDto.getLanguage(), result.getLanguage());
        assertEquals(bookDto.getAuthorId(), result.getAuthorId());
        assertEquals(bookDto.getGenreId(), result.getGenreId());

        verify(bookRepository).save(bookEntity);
        verify(bookMapper).dtoToEntity(bookToCreate);
        verify(bookMapper).entityToDto(savedEntity);
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