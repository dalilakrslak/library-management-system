package ba.unsa.etf.transfer.core.mapper;

import ba.unsa.etf.transfer.api.model.Library;
import ba.unsa.etf.transfer.dao.model.LibraryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LibraryMapper {
    Library entityToDto(LibraryEntity libraryEntity);

    LibraryEntity dtoToEntity(Library library);

    void updateEntity(Library library, @MappingTarget LibraryEntity libraryEntity);
}
