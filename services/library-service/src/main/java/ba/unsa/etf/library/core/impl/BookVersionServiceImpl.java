package ba.unsa.etf.library.core.impl;

import ba.unsa.etf.library.api.model.BookVersion;
import ba.unsa.etf.library.api.service.BookVersionService;
import ba.unsa.etf.library.core.mapper.BookVersionMapper;
import ba.unsa.etf.library.core.validation.BookVersionValidation;
import ba.unsa.etf.library.dao.model.BookVersionEntity;
import ba.unsa.etf.library.dao.repository.BookVersionRepository;
import lombok.AllArgsConstructor;
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
}
