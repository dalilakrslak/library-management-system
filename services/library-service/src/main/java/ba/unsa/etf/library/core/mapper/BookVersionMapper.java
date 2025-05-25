package ba.unsa.etf.library.core.mapper;

import ba.unsa.etf.library.api.model.BookVersion;
import ba.unsa.etf.library.dao.model.BookVersionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookVersionMapper {
    @Mapping(source = "library.id", target = "libraryId")
    BookVersion entityToDto(BookVersionEntity bookVersionEntity);

    @Mapping(source = "libraryId", target = "library.id")
    BookVersionEntity dtoToEntity(BookVersion bookVersion);

    @Mapping(source = "libraryId", target = "library.id")
    void updateEntity(BookVersion bookVersion, @MappingTarget BookVersionEntity bookVersionEntity);
}
