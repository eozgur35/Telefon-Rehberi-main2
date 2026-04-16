package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.UserDao;
import duzce.bm.mf.telefonrehberi.model.User;
import duzce.bm.mf.telefonrehberi.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = RuntimeException.class)
public class UserService implements IUserService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAllUsers() {
        return userDao.userleriYukle();
    }

    @Override
    @Transactional(readOnly = false)
    public void saveUser(User user) {
        userDao.saveOrUpdate(user);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteUser(int id) {
        try {
            userDao.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password);
    }
}
