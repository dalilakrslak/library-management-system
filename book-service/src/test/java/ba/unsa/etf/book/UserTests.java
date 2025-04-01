package ba.unsa.etf.book;

import ba.unsa.etf.book.api.model.User;
import ba.unsa.etf.book.core.impl.UserServiceImpl;
import ba.unsa.etf.book.core.mapper.UserMapper;
import ba.unsa.etf.book.core.validation.UserValidation;
import ba.unsa.etf.book.dao.model.UserEntity;
import ba.unsa.etf.book.dao.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserValidation userValidation;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity userEntity;
    private User userDto;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity(1L, "John", "Doe", "johndoe@email.com", "password", "060123456");
        userDto = new User(1L, "John", "Doe", "johndoe@email.com", "password", "060123456");
    }

    @Test
    void testFindAll() {
        // Arrange
        List<UserEntity> userEntities = List.of(
                new UserEntity(1L, "John", "Doe", "johndoe@email.com", "password", "060123456"),
                new UserEntity(2L, "John2", "Doe2", "johndoe2@email.com", "password", "060123457")
        );

        List<User> userDtos = List.of(
                new User(1L, "John", "Doe", "johndoe@email.com", "password", "060123456"),
                new User(2L, "John2", "Doe2", "johndoe2@email.com", "password", "060123457")
        );

        when(userRepository.findAll()).thenReturn(userEntities);
        when(userMapper.entityToDto(userEntities.get(0))).thenReturn(userDtos.get(0));
        when(userMapper.entityToDto(userEntities.get(1))).thenReturn(userDtos.get(1));

        // Act
        List<User> result = userService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(userDtos.get(0).getId(), result.get(0).getId());
        assertEquals(userDtos.get(1).getId(), result.get(1).getId());

        verify(userRepository).findAll();
        verify(userMapper, times(2)).entityToDto(any(UserEntity.class));
    }


    @Test
    void testFindById_AuthorExists() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userMapper.entityToDto(userEntity)).thenReturn(userDto);

        User result = userService.findById(id);

        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getFirstName(), result.getFirstName());
        assertEquals(userDto.getLastName(), result.getLastName());
        assertEquals(userDto.getEmail(), result.getEmail());
        assertEquals(userDto.getPassword(), result.getPassword());
        assertEquals(userDto.getPhone(), result.getPhone());

        verify(userValidation).exists(id);
        verify(userRepository).findById(id);
        verify(userMapper).entityToDto(userEntity);
    }

    @Test
    void testFindById_AuthorNotFound() {
        Long id = 2L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        User result = userService.findById(id);

        assertNull(result);
        verify(userValidation).exists(id);
        verify(userRepository).findById(id);
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    void testCreate() {
        // Arrange
        User userToCreate = new User(1L, "John", "Doe", "johndoe@email.com", "password", "060123456");
        UserEntity userEntity = new UserEntity(1L, "John", "Doe", "johndoe@email.com", "password", "060123456");
        UserEntity savedEntity = new UserEntity(1L, "John", "Doe", "johndoe@email.com", "password", "060123456");
        User userDto = new User(1L, "John", "Doe", "johndoe@email.com", "password", "060123456");

        when(userMapper.dtoToEntity(userToCreate)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(savedEntity);
        when(userMapper.entityToDto(savedEntity)).thenReturn(userDto);

        // Act
        User result = userService.create(userToCreate);

        // Assert
        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getFirstName(), result.getFirstName());
        assertEquals(userDto.getLastName(), result.getLastName());
        assertEquals(userDto.getEmail(), result.getEmail());
        assertEquals(userDto.getPassword(), result.getPassword());
        assertEquals(userDto.getPhone(), result.getPhone());

        verify(userRepository).save(userEntity);
        verify(userMapper).dtoToEntity(userToCreate);
        verify(userMapper).entityToDto(savedEntity);
    }

    @Test
    void testUpdate_AuthorExists() {
        // Arrange
        User userToUpdate = new User(1L, "John", "Doe", "johndoe@email.com", "password", "060123456");
        UserEntity userEntity = new UserEntity(1L, "John", "Doe", "johndoe@email.com", "password", "060123456");
        UserEntity updatedEntity = new UserEntity(1L, "John", "Doe", "johndoe@email.com", "password", "060123456");
        User userDto = new User(1L, "John", "Doe", "johndoe@email.com", "password", "060123456");

        when(userMapper.dtoToEntity(userToUpdate)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(updatedEntity);
        when(userMapper.entityToDto(updatedEntity)).thenReturn(userDto);

        // Act
        User result = userService.update(userToUpdate);

        // Assert
        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getFirstName(), result.getFirstName());
        assertEquals(userDto.getLastName(), result.getLastName());
        assertEquals(userDto.getEmail(), result.getEmail());
        assertEquals(userDto.getPassword(), result.getPassword());
        assertEquals(userDto.getPhone(), result.getPhone());

        verify(userRepository).save(userEntity);
        verify(userMapper).dtoToEntity(userToUpdate);
        verify(userMapper).entityToDto(updatedEntity);
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;

        doNothing().when(userRepository).deleteById(id);

        // Act
        userService.delete(id);

        // Assert
        verify(userValidation).exists(id);
        verify(userRepository).deleteById(id);
    }
}

