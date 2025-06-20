package ba.unsa.etf.book.core.impl;

import ba.unsa.etf.book.api.model.BookVersion;
import ba.unsa.etf.book.api.service.BookVersionService;
import ba.unsa.etf.book.core.mapper.BookVersionMapper;
import ba.unsa.etf.book.core.validation.BookVersionValidation;
import ba.unsa.etf.book.dao.model.BookVersionEntity;
import ba.unsa.etf.book.dao.repository.BookVersionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookVersionServiceImpl implements BookVersionService {
    private final BookVersionRepository bookVersionRepository;
    private final BookVersionMapper bookVersionMapper;
    private final BookVersionValidation bookVersionValidation;

    @Override
    public List<BookVersion> findAll() {
        return bookVersionRepository.findAll()
                .stream()
                .map(bookVersionMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookVersion findById(String isbn) {
        bookVersionValidation.exists(isbn);
        return bookVersionRepository.findById(isbn).map(bookVersionMapper::entityToDto).orElse(null);
    }

    @Override
    @Transactional
    public BookVersion create(BookVersion bookVersion) {
        BookVersionEntity bookVersionEntity = bookVersionMapper.dtoToEntity(bookVersion);
        bookVersionRepository.save(bookVersionEntity);
        return bookVersionMapper.entityToDto(bookVersionEntity);
    }

    @Override
    @Transactional
    public BookVersion update(BookVersion bookVersion) {
        BookVersionEntity bookVersionEntity = bookVersionMapper.dtoToEntity(bookVersion);
        bookVersionRepository.save(bookVersionEntity);
        return bookVersionMapper.entityToDto(bookVersionEntity);
    }

    @Override
    @Transactional
    public void delete(String isbn) {
        bookVersionValidation.exists(isbn);
        bookVersionRepository.deleteById(isbn);
    }

    @Override
    public Page<BookVersion> getAllBooks(Pageable pageable) {
        return bookVersionRepository.findAll(pageable).map(bookVersionMapper::entityToDto);
    }

    @Override
    public List<BookVersion> createBatch(List<BookVersion> bookVersions) {
        List<BookVersionEntity> bookVersionEntities = bookVersions.stream().map(bookVersionMapper::dtoToEntity).collect(Collectors.toList());
        bookVersionRepository.saveAll(bookVersionEntities);
        return bookVersionEntities.stream().map(bookVersionMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<BookVersion> findBooksByTitle(String title) {
        List<BookVersionEntity> bookVersionEntities = bookVersionRepository.findBooksByTitle(title);
        return bookVersionEntities.stream().map(bookVersionMapper::entityToDto).collect(Collectors.toList());
    }
}
