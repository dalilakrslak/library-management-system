package ba.unsa.etf.book.core.impl;

import ba.unsa.etf.book.api.model.Book;
import ba.unsa.etf.book.api.service.BookService;
import ba.unsa.etf.book.core.mapper.BookMapper;
import ba.unsa.etf.book.core.validation.BookValidation;
import ba.unsa.etf.book.dao.model.BookEntity;
import ba.unsa.etf.book.dao.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookValidation bookValidation;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Book findById(Long id) {
        bookValidation.exists(id);
        return bookRepository.findById(id).map(bookMapper::entityToDto).orElse(null);
    }

    @Override
    @Transactional
    public Book create(Book book) {
        BookEntity bookEntity = bookMapper.dtoToEntity(book);
        bookRepository.save(bookEntity);
        return bookMapper.entityToDto(bookEntity);
    }

    @Override
    @Transactional
    public Book update(Book book) {
        BookEntity bookEntity = bookMapper.dtoToEntity(book);
        bookRepository.save(bookEntity);
        return bookMapper.entityToDto(bookEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        bookValidation.exists(id);
        bookRepository.deleteById(id);
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(bookMapper::entityToDto);
    }

    @Override
    public List<Book> createBatch(List<Book> books) {
        List<BookEntity> bookEntities = books.stream().map(bookMapper::dtoToEntity).collect(Collectors.toList());
        bookEntities.addAll(bookRepository.saveAll(bookEntities));
        return bookEntities.stream().map(bookMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<Book> findBooksByAuthor(String authorName) {
        List<BookEntity> bookEntities = bookRepository.findBooksByAuthor(authorName);
        return bookEntities.stream().map(bookMapper::entityToDto).collect(Collectors.toList());
    }
}
