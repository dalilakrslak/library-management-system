package ba.unsa.etf.library.core.mapper;

import ba.unsa.etf.library.api.model.Role;
import ba.unsa.etf.library.dao.model.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    Role entityToDto(RoleEntity roleEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    RoleEntity dtoToEntity(Role role);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    void updateEntity(Role role, @MappingTarget RoleEntity roleEntity);
}
