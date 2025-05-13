package ba.unsa.etf.book.core.mapper;

import ba.unsa.etf.book.api.model.User;
import ba.unsa.etf.book.dao.model.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-13T10:55:29+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User entityToDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        Long id = null;
        String firstName = null;
        String lastName = null;
        String email = null;
        String password = null;
        String phone = null;

        id = userEntity.getId();
        firstName = userEntity.getFirstName();
        lastName = userEntity.getLastName();
        email = userEntity.getEmail();
        password = userEntity.getPassword();
        phone = userEntity.getPhone();

        User user = new User( id, firstName, lastName, email, password, phone );

        return user;
    }

    @Override
    public UserEntity dtoToEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( user.getId() );
        userEntity.setFirstName( user.getFirstName() );
        userEntity.setLastName( user.getLastName() );
        userEntity.setEmail( user.getEmail() );
        userEntity.setPassword( user.getPassword() );
        userEntity.setPhone( user.getPhone() );

        return userEntity;
    }

    @Override
    public void updateEntity(User user, UserEntity userEntity) {
        if ( user == null ) {
            return;
        }

        userEntity.setId( user.getId() );
        userEntity.setFirstName( user.getFirstName() );
        userEntity.setLastName( user.getLastName() );
        userEntity.setEmail( user.getEmail() );
        userEntity.setPassword( user.getPassword() );
        userEntity.setPhone( user.getPhone() );
    }
}
