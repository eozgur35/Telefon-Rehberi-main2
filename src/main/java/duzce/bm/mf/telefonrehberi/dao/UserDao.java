package duzce.bm.mf.telefonrehberi.dao;

import duzce.bm.mf.telefonrehberi.model.User;
import java.util.List;

public interface UserDao {
    Boolean saveOrUpdate(User user);
    List<User> userleriYukle();
    User getById(int id);
    void delete(int id);

    // Admin Login icin kullanici adi sifre sorgusu
    User findByEmailAndPassword(String email, String password);
}