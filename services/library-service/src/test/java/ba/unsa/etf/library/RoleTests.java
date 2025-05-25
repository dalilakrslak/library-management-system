package ba.unsa.etf.library;

import ba.unsa.etf.library.api.model.Role;
import ba.unsa.etf.library.core.impl.RoleServiceImpl;
import ba.unsa.etf.library.core.mapper.RoleMapper;
import ba.unsa.etf.library.core.validation.RoleValidation;
import ba.unsa.etf.library.dao.model.RoleEntity;
import ba.unsa.etf.library.dao.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleTests {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private RoleValidation roleValidation;

    @InjectMocks
    private RoleServiceImpl roleService;

    private RoleEntity roleEntity;
    private Role roleDto;

    @BeforeEach
    void setUp() {
        roleEntity = new RoleEntity(1L, "ADMIN");
        roleDto = new Role(1L, "ADMIN");
    }

    @Test
    void testFindAll_RolesExist() {
        when(roleRepository.findAll()).thenReturn(List.of(roleEntity));
        when(roleMapper.entityToDto(roleEntity)).thenReturn(roleDto);

        List<Role> result = roleService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ADMIN", result.get(0).getName());

        verify(roleRepository).findAll();
        verify(roleMapper).entityToDto(roleEntity);
    }

    @Test
    void testFindAll_NoRolesExist() {
        when(roleRepository.findAll()).thenReturn(List.of());

        List<Role> result = roleService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(roleRepository).findAll();
        verifyNoInteractions(roleMapper);
    }

    @Test
    void testFindById_RoleExists() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(roleEntity));
        when(roleMapper.entityToDto(roleEntity)).thenReturn(roleDto);

        Role result = roleService.findById(1L);

        assertNotNull(result);
        assertEquals("ADMIN", result.getName());

        verify(roleValidation).exists(1L);
        verify(roleRepository).findById(1L);
        verify(roleMapper).entityToDto(roleEntity);
    }

    @Test
    void testFindById_RoleDoesNotExist() {
        when(roleRepository.findById(2L)).thenReturn(Optional.empty());

        Role result = roleService.findById(2L);

        assertNull(result);

        verify(roleValidation).exists(2L);
        verify(roleRepository).findById(2L);
        verifyNoMoreInteractions(roleMapper);
    }

    @Test
    void testCreateRole() {
        when(roleMapper.dtoToEntity(roleDto)).thenReturn(roleEntity);
        when(roleRepository.save(roleEntity)).thenReturn(roleEntity);
        when(roleMapper.entityToDto(roleEntity)).thenReturn(roleDto);

        Role result = roleService.create(roleDto);

        assertNotNull(result);
        assertEquals("ADMIN", result.getName());

        verify(roleRepository).save(roleEntity);
        verify(roleMapper).dtoToEntity(roleDto);
        verify(roleMapper).entityToDto(roleEntity);
    }

    @Test
    void testUpdateRole() {
        when(roleMapper.dtoToEntity(roleDto)).thenReturn(roleEntity);
        when(roleRepository.save(roleEntity)).thenReturn(roleEntity);
        when(roleMapper.entityToDto(roleEntity)).thenReturn(roleDto);

        Role result = roleService.update(roleDto);

        assertNotNull(result);
        assertEquals("ADMIN", result.getName());

        verify(roleRepository).save(roleEntity);
        verify(roleMapper).dtoToEntity(roleDto);
        verify(roleMapper).entityToDto(roleEntity);
    }

    @Test
    void testDeleteRole() {
        doNothing().when(roleValidation).exists(1L);
        doNothing().when(roleRepository).deleteById(1L);

        roleService.delete(1L);

        verify(roleValidation).exists(1L);
        verify(roleRepository).deleteById(1L);
    }
}
