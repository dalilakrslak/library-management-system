package ba.unsa.etf.library;

import ba.unsa.etf.library.api.model.User;
import ba.unsa.etf.library.core.impl.UserServiceImpl;
import ba.unsa.etf.library.core.mapper.UserMapper;
import ba.unsa.etf.library.core.validation.UserValidation;
import ba.unsa.etf.library.dao.model.UserEntity;
import ba.unsa.etf.library.dao.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTests {

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
        userEntity = new UserEntity(1L, "John", "Doe", "john.doe@example.com", "password123", "123456789", null, null);
        userDto = new User(1L, "John", "Doe", "john.doe@example.com", "password123", 1L, null);
    }

    @Test
    void testFindById_UserExists() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userMapper.entityToDto(userEntity)).thenReturn(userDto);

        User result = userService.findById(id);

        assertNotNull(result);
        assertEquals(userDto.getEmail(), result.getEmail());

        verify(userValidation).exists(id);
        verify(userRepository).findById(id);
        verify(userMapper).entityToDto(userEntity);
    }

    @Test
    void testFindById_UserNotFound() {
        Long id = 2L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        User result = userService.findById(id);

        assertNull(result);
        verify(userValidation).exists(id);
        verify(userRepository).findById(id);
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    void testCreate_ValidUser() {
        when(userMapper.dtoToEntity(userDto)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.entityToDto(userEntity)).thenReturn(userDto);

        User result = userService.create(userDto);

        assertNotNull(result);
        assertEquals(userDto.getEmail(), result.getEmail());

        verify(userValidation).validateEmail(userDto.getEmail());
        verify(userRepository).save(userEntity);
        verify(userMapper).entityToDto(userEntity);
    }

    @Test
    void testCreate_InvalidEmail() {
        userDto.setEmail("invalid-email");
        doThrow(new IllegalArgumentException("Invalid email format")).when(userValidation).validateEmail(userDto.getEmail());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.create(userDto));
        assertEquals("Invalid email format", exception.getMessage());

        verify(userValidation).validateEmail(userDto.getEmail());
        verifyNoInteractions(userRepository);
    }

    @Test
    void testDelete() {
        Long id = 1L;

        doNothing().when(userRepository).deleteById(id);

        userService.delete(id);

        verify(userValidation).exists(id);
        verify(userRepository).deleteById(id);
    }
}
