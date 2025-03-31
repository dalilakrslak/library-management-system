package ba.unsa.etf.library.api.service;

import ba.unsa.etf.library.api.model.Role;
import java.util.List;

public interface RoleService {
    List<Role> findAll();

    Role findById(Long id);

    Role create(Role role);

    Role update(Role role);

    void delete(Long id);
}