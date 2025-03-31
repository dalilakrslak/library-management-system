package ba.unsa.etf.library.core.mapper;

import ba.unsa.etf.library.api.model.Library;
import ba.unsa.etf.library.dao.model.LibraryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LibraryMapper {
    Library entityToDto(LibraryEntity libraryEntity);

    LibraryEntity dtoToEntity(Library library);

    void updateEntity(Library library, @MappingTarget LibraryEntity libraryEntity);
}
