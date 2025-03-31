package ba.unsa.etf.book.api.service;

import ba.unsa.etf.book.api.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User create(User user);

    User update(User user);

    void delete(Long id);
}
