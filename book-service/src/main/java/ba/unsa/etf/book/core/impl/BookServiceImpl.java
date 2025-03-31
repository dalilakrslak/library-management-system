package ba.unsa.etf.book.core.impl;

import ba.unsa.etf.book.api.model.Book;
import ba.unsa.etf.book.api.service.BookService;
import ba.unsa.etf.book.core.mapper.BookMapper;
import ba.unsa.etf.book.core.validation.BookValidation;
import ba.unsa.etf.book.dao.model.BookEntity;
import ba.unsa.etf.book.dao.repository.BookRepository;
import lombok.AllArgsConstructor;
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
        if (!bookRepository.existsById(book.getId())) {
            return null;
        }
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
}
