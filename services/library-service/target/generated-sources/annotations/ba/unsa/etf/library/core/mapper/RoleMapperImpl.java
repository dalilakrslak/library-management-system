package ba.unsa.etf.library.core.mapper;

import ba.unsa.etf.library.api.model.Role;
import ba.unsa.etf.library.dao.model.RoleEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-08T21:48:11+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role entityToDto(RoleEntity roleEntity) {
        if ( roleEntity == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = roleEntity.getId();
        name = roleEntity.getName();

        Role role = new Role( id, name );

        return role;
    }

    @Override
    public RoleEntity dtoToEntity(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleEntity roleEntity = new RoleEntity();

        roleEntity.setId( role.getId() );
        roleEntity.setName( role.getName() );

        return roleEntity;
    }

    @Override
    public void updateEntity(Role role, RoleEntity roleEntity) {
        if ( role == null ) {
            return;
        }

        roleEntity.setId( role.getId() );
        roleEntity.setName( role.getName() );
    }
}
