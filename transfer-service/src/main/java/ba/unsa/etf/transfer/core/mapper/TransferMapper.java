package ba.unsa.etf.transfer.core.mapper;

import ba.unsa.etf.transfer.api.model.Transfer;
import ba.unsa.etf.transfer.dao.model.TransferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TransferMapper {
    @Mappings({
            @Mapping(source = "bookVersion.isbn", target = "bookVersionISBN"),
            @Mapping(source = "libraryFrom.id", target = "libraryFrom"),
            @Mapping(source = "libraryTo.id", target = "libraryTo")
    })
    Transfer entityToDto(TransferEntity transferEntity);

    @Mappings({
            @Mapping(source = "bookVersionISBN", target = "bookVersion.isbn"),
            @Mapping(source = "libraryFrom", target = "libraryFrom.id"),
            @Mapping(source = "libraryTo", target = "libraryTo.id")
    })
    TransferEntity dtoToEntity(Transfer transfer);

    @Mappings({
            @Mapping(source = "bookVersionISBN", target = "bookVersion.isbn"),
            @Mapping(source = "libraryFrom", target = "libraryFrom.id"),
            @Mapping(source = "libraryTo", target = "libraryTo.id")
    })
    void updateEntity(Transfer transfer, @MappingTarget TransferEntity transferEntity);
}
