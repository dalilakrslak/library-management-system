package ba.unsa.etf.library.core.mapper;

import ba.unsa.etf.library.api.model.User;
import ba.unsa.etf.library.dao.model.LibraryEntity;
import ba.unsa.etf.library.dao.model.RoleEntity;
import ba.unsa.etf.library.dao.model.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-08T21:48:11+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User entityToDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        Long roleId = null;
        Long libraryId = null;
        Long id = null;
        String firstName = null;
        String lastName = null;
        String email = null;
        String phone = null;

        roleId = userEntityRoleId( userEntity );
        libraryId = userEntityLibraryId( userEntity );
        id = userEntity.getId();
        firstName = userEntity.getFirstName();
        lastName = userEntity.getLastName();
        email = userEntity.getEmail();
        phone = userEntity.getPhone();

        User user = new User( id, firstName, lastName, email, phone, roleId, libraryId );

        return user;
    }

    @Override
    public UserEntity dtoToEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setRole( userToRoleEntity( user ) );
        userEntity.setLibrary( userToLibraryEntity( user ) );
        userEntity.setId( user.getId() );
        userEntity.setFirstName( user.getFirstName() );
        userEntity.setLastName( user.getLastName() );
        userEntity.setEmail( user.getEmail() );
        userEntity.setPhone( user.getPhone() );

        return userEntity;
    }

    @Override
    public void updateEntity(User user, UserEntity userEntity) {
        if ( user == null ) {
            return;
        }

        if ( userEntity.getRole() == null ) {
            userEntity.setRole( new RoleEntity() );
        }
        userToRoleEntity1( user, userEntity.getRole() );
        if ( userEntity.getLibrary() == null ) {
            userEntity.setLibrary( new LibraryEntity() );
        }
        userToLibraryEntity1( user, userEntity.getLibrary() );
        userEntity.setId( user.getId() );
        userEntity.setFirstName( user.getFirstName() );
        userEntity.setLastName( user.getLastName() );
        userEntity.setEmail( user.getEmail() );
        userEntity.setPhone( user.getPhone() );
    }

    private Long userEntityRoleId(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }
        RoleEntity role = userEntity.getRole();
        if ( role == null ) {
            return null;
        }
        Long id = role.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long userEntityLibraryId(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }
        LibraryEntity library = userEntity.getLibrary();
        if ( library == null ) {
            return null;
        }
        Long id = library.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected RoleEntity userToRoleEntity(User user) {
        if ( user == null ) {
            return null;
        }

        RoleEntity roleEntity = new RoleEntity();

        roleEntity.setId( user.getRoleId() );

        return roleEntity;
    }

    protected LibraryEntity userToLibraryEntity(User user) {
        if ( user == null ) {
            return null;
        }

        LibraryEntity libraryEntity = new LibraryEntity();

        libraryEntity.setId( user.getLibraryId() );

        return libraryEntity;
    }

    protected void userToRoleEntity1(User user, RoleEntity mappingTarget) {
        if ( user == null ) {
            return;
        }

        mappingTarget.setId( user.getRoleId() );
    }

    protected void userToLibraryEntity1(User user, LibraryEntity mappingTarget) {
        if ( user == null ) {
            return;
        }

        mappingTarget.setId( user.getLibraryId() );
    }
}
