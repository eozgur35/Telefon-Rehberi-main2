package duzce.bm.mf.telefonrehberi.services;

import duzce.bm.mf.telefonrehberi.model.User;
import java.util.List;

public interface IUserService {
    List<User> getAllUsers();
    void saveUser(User user);
    boolean deleteUser(int id);
    User login(String email, String password);
}