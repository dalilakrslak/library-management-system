package ba.unsa.etf.book.core.impl;

import ba.unsa.etf.book.api.model.Author;
import ba.unsa.etf.book.api.service.AuthorService;
import ba.unsa.etf.book.core.mapper.AuthorMapper;
import ba.unsa.etf.book.core.validation.AuthorValidation;
import ba.unsa.etf.book.dao.model.AuthorEntity;
import ba.unsa.etf.book.dao.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final AuthorValidation authorValidation;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Author findById(Long id) {
        authorValidation.exists(id);
        return authorRepository.findById(id).map(authorMapper::entityToDto).orElse(null);
    }

    @Override
    @Transactional
    public Author create(Author author) {
        authorValidation.validateCreate(author);
        AuthorEntity authorEntity = authorMapper.dtoToEntity(author);
        authorRepository.save(authorEntity);
        return authorMapper.entityToDto(authorEntity);
    }

    @Override
    @Transactional
    public Author update(Author author) {
        authorValidation.validateUpdate(author);
        AuthorEntity authorEntity = authorMapper.dtoToEntity(author);
        authorRepository.save(authorEntity);
        return authorMapper.entityToDto(authorEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        authorValidation.exists(id);
        authorRepository.deleteById(id);
    }

    @Override
    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable).map(authorMapper::entityToDto);
    }

    @Override
    public List<Author> createBatch(List<Author> authors) {
        List<AuthorEntity> entities = authors.stream()
                .map(authorMapper::dtoToEntity)
                .toList();
        List<AuthorEntity> saved = authorRepository.saveAll(entities);
        return saved.stream().map(authorMapper::entityToDto).toList();
    }

    @Override
    public List<Author> findByBiographyKeyword(String keyword) {
        List<AuthorEntity> results = authorRepository.findByBiographyKeyword(keyword);
        return results.stream().map(authorMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<Author> searchAuthors(String firstName, String lastName, String biography) {
        List<AuthorEntity> authorEntities = authorRepository.searchAuthors(firstName, lastName, biography);

        return authorEntities.stream()
                .map(authorMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
