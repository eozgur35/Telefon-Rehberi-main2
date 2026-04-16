package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.UserDao;
import duzce.bm.mf.telefonrehberi.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Boolean saveOrUpdate(User user) {
        try {
            if (user.getUserId() == 0) {
                getCurrentSession().persist(user);
            } else {
                getCurrentSession().merge(user);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<User> userleriYukle() {
        return getCurrentSession().createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getById(int id) {
        return getCurrentSession().get(User.class, id);
    }

    @Override
    public void delete(int id) {
        User user = getById(id);
        if (user != null) getCurrentSession().remove(user);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        try {
            return getCurrentSession()
                    .createQuery("from User u where u.email = :email and u.password = :password", User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .uniqueResult();
        } catch (Exception e) {
            return null;
        }
    }
}