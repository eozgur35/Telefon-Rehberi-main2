package duzce.bm.mf.telefonrehberi.dao.impl;

import duzce.bm.mf.telefonrehberi.dao.OtpDao;
import duzce.bm.mf.telefonrehberi.entity.Otp;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class OtpDaoImpl implements OtpDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Otp findTopByEmailOrderByIdDesc(String email) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Otp> criteriaQuery = criteriaBuilder.createQuery(Otp.class);
        Root<Otp> root = criteriaQuery.from(Otp.class);
        Predicate predicateDepartment = criteriaBuilder.equal(root.get("email"), email);
        criteriaQuery.select(root).where(predicateDepartment).orderBy(criteriaBuilder.desc(root.get("id")));
        Query<Otp> query = session.createQuery(criteriaQuery);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    @Override
    public void save(Otp otp) {
        try {
            sessionFactory.getCurrentSession().save(otp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Otp otp) {
        if (Objects.nonNull(otp)) {
            sessionFactory.getCurrentSession().remove(otp);
        }
    }

}
