package ba.unsa.etf.book.api.service;

import ba.unsa.etf.book.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User create(User user);

    User update(User user);

    void delete(Long id);

    Page<User> getAllUsers(Pageable pageable);

    List<User> createBatch(List<User> users);

    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    User findByEmail(String email);
}
