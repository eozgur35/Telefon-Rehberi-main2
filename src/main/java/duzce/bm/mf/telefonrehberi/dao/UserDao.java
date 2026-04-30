package duzce.bm.mf.telefonrehberi.dao;

import duzce.bm.mf.telefonrehberi.entity.User;

public interface UserDao {
    User findByEmail(String email);
}
