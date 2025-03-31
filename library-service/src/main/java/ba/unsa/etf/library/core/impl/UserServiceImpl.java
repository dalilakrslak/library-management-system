package ba.unsa.etf.library.core.impl;

import ba.unsa.etf.library.api.model.User;
import ba.unsa.etf.library.api.service.UserService;
import ba.unsa.etf.library.core.mapper.UserMapper;
import ba.unsa.etf.library.core.validation.UserValidation;
import ba.unsa.etf.library.dao.model.UserEntity;
import ba.unsa.etf.library.dao.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidation userValidation;

    @Override
    public List<User> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        userValidation.exists(id);
        return userRepository.findById(id).map(userMapper::entityToDto).orElse(null);
    }

    @Override
    @Transactional
    public User create(User user) {
        UserEntity userEntity = userMapper.dtoToEntity(user);
        userRepository.save(userEntity);
        return userMapper.entityToDto(userEntity);
    }

    @Override
    @Transactional
    public User update(User user) {
        UserEntity userEntity = userMapper.dtoToEntity(user);
        userRepository.save(userEntity);
        return userMapper.entityToDto(userEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userValidation.exists(id);
        userRepository.deleteById(id);
    }
}
