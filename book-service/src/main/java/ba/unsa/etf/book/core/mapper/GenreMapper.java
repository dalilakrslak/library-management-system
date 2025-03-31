package ba.unsa.etf.book.core.mapper;

import ba.unsa.etf.book.api.model.Genre;
import ba.unsa.etf.book.dao.model.GenreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    Genre entityToDto(GenreEntity genreEntity);

    GenreEntity dtoToEntity(Genre genre);

    void updateEntity(Genre genre, @MappingTarget GenreEntity genreEntity);
}
