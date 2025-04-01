package ba.unsa.etf.library.api.service;

import ba.unsa.etf.library.api.model.User;
import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User create(User user);

    User update(User user);

    void delete(Long id);
}