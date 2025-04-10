package ba.unsa.etf.book.core.impl;

import ba.unsa.etf.book.api.model.Genre;
import ba.unsa.etf.book.api.service.GenreService;
import ba.unsa.etf.book.core.mapper.GenreMapper;
import ba.unsa.etf.book.core.validation.GenreValidation;
import ba.unsa.etf.book.dao.model.GenreEntity;
import ba.unsa.etf.book.dao.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private final GenreValidation genreValidation;

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll()
                .stream()
                .map(genreMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Genre findById(Long id) {
        genreValidation.exists(id);
        return genreRepository.findById(id).map(genreMapper::entityToDto).orElse(null);
    }

    @Override
    @Transactional
    public Genre create(Genre genre) {
        GenreEntity genreEntity = genreMapper.dtoToEntity(genre);
        genreRepository.save(genreEntity);
        return genreMapper.entityToDto(genreEntity);
    }

    @Override
    @Transactional
    public Genre update(Genre genre) {
        GenreEntity genreEntity = genreMapper.dtoToEntity(genre);
        genreRepository.save(genreEntity);
        return genreMapper.entityToDto(genreEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        genreValidation.exists(id);
        genreRepository.deleteById(id);
    }

    @Override
    public Page<Genre> getAllGenres(Pageable pageable) {
        return genreRepository.findAll(pageable).map(genreMapper::entityToDto);
    }

    @Override
    public List<Genre> createBatch(List<Genre> genres) {
        List<GenreEntity> entities = genres.stream().map(genreMapper::dtoToEntity).collect(Collectors.toList());
        List<GenreEntity> newGenres = genreRepository.saveAll(entities);
        return newGenres.stream().map(genreMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<Genre> findByName(String name) {
        List<GenreEntity> entities = genreRepository.findByName(name);
        return entities.stream().map(genreMapper::entityToDto).collect(Collectors.toList());
    }
}
