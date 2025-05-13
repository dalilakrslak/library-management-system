package ba.unsa.etf.library.core.impl;

import ba.unsa.etf.library.api.model.Role;
import ba.unsa.etf.library.api.service.RoleService;
import ba.unsa.etf.library.core.mapper.RoleMapper;
import ba.unsa.etf.library.core.validation.RoleValidation;
import ba.unsa.etf.library.dao.model.RoleEntity;
import ba.unsa.etf.library.dao.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final RoleValidation roleValidation;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Role findById(Long id) {
        roleValidation.exists(id);
        return roleRepository.findById(id).map(roleMapper::entityToDto).orElse(null);
    }

    @Override
    @Transactional
    public Role create(Role role) {
        RoleEntity roleEntity = roleMapper.dtoToEntity(role);
        roleRepository.save(roleEntity);
        return roleMapper.entityToDto(roleEntity);
    }

    @Override
    @Transactional
    public Role update(Role role) {
        RoleEntity roleEntity = roleMapper.dtoToEntity(role);
        roleRepository.save(roleEntity);
        return roleMapper.entityToDto(roleEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        roleValidation.exists(id);
        roleRepository.deleteById(id);
    }
}
