package ba.unsa.etf.library.core.mapper;

import ba.unsa.etf.library.api.model.User;
import ba.unsa.etf.library.dao.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "role.id", target = "roleId"),
            @Mapping(source = "library.id", target = "libraryId")
    })
    User entityToDto(UserEntity userEntity);

    @Mappings({
            @Mapping(source = "roleId", target = "role.id"),
            @Mapping(source = "libraryId", target = "library.id")
    })
    UserEntity dtoToEntity(User user);

    @Mappings({
            @Mapping(source = "roleId", target = "role.id"),
            @Mapping(source = "libraryId", target = "library.id")
    })
    void updateEntity(User user, @MappingTarget UserEntity userEntity);
}
