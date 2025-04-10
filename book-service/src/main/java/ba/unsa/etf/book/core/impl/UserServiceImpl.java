package ba.unsa.etf.book.core.impl;

import ba.unsa.etf.book.api.model.User;
import ba.unsa.etf.book.api.service.UserService;
import ba.unsa.etf.book.core.mapper.UserMapper;
import ba.unsa.etf.book.core.validation.UserValidation;
import ba.unsa.etf.book.dao.model.UserEntity;
import ba.unsa.etf.book.dao.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::entityToDto);
    }

    @Override
    public List<User> createBatch(List<User> users) {
        List<UserEntity> userEntities = users.stream().map(userMapper::dtoToEntity).collect(Collectors.toList());
        userEntities = userRepository.saveAll(userEntities);
        return userEntities.stream().map(userMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<User> findByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName).stream().map(userMapper::entityToDto).toList();
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.entityToDto(userRepository.findByEmail(email));
    }
}
