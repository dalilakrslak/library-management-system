package ba.unsa.etf.transfer.core.mapper;

import ba.unsa.etf.transfer.api.model.Author;
import ba.unsa.etf.transfer.dao.model.AuthorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author entityToDto(AuthorEntity authorEntity);

    AuthorEntity dtoToEntity(Author author);

    void updateEntity(Author author, @MappingTarget AuthorEntity authorEntity);
}
