package ba.unsa.etf.transfer.core.impl;

import ba.unsa.etf.transfer.api.model.Author;
import ba.unsa.etf.transfer.api.service.AuthorService;
import ba.unsa.etf.transfer.core.mapper.AuthorMapper;
import ba.unsa.etf.transfer.core.validation.AuthorValidation;
import ba.unsa.etf.transfer.dao.model.AuthorEntity;
import ba.unsa.etf.transfer.dao.repository.AuthorRepository;
import lombok.AllArgsConstructor;
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
        if (!authorRepository.existsById(author.getId())) {
            return null;
        }
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
}
