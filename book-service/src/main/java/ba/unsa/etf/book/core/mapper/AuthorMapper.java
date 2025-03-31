package ba.unsa.etf.book.core.mapper;

import ba.unsa.etf.book.api.model.Author;
import ba.unsa.etf.book.dao.model.AuthorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author entityToDto(AuthorEntity authorEntity);

    AuthorEntity dtoToEntity(Author author);

    void updateEntity(Author author, @MappingTarget AuthorEntity authorEntity);
}
