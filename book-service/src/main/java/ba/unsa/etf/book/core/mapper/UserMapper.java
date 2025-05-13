package ba.unsa.etf.book.core.mapper;

import ba.unsa.etf.book.api.model.User;
import ba.unsa.etf.book.dao.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User entityToDto(UserEntity userEntity);

    UserEntity dtoToEntity(User user);

    void updateEntity(User user, @MappingTarget UserEntity userEntity);
}
