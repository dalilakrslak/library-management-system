package ba.unsa.etf.transfer.core.mapper;

import ba.unsa.etf.transfer.api.model.Transfer;
import ba.unsa.etf.transfer.dao.model.TransferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TransferMapper {
    Transfer entityToDto(TransferEntity transferEntity);

    TransferEntity dtoToEntity(Transfer transfer);

    void updateEntity(Transfer transfer, @MappingTarget TransferEntity transferEntity);
}
