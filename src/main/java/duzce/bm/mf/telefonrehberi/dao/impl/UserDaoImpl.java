package duzce.bm.mf.telefonrehberi.dao.impl;

import duzce.bm.mf.telefonrehberi.dao.UserDao;
import duzce.bm.mf.telefonrehberi.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public User findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        Predicate predicateEmail = criteriaBuilder.equal(root.get("email"), email);
        criteriaQuery.select(root).where(predicateEmail);
        Query<User> query = session.createQuery(criteriaQuery);
        return query.getSingleResult();
    }
}
