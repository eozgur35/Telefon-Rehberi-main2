package duzce.bm.mf.telefonrehberi.dao.impl;

import duzce.bm.mf.telefonrehberi.dao.PersonDao;
import duzce.bm.mf.telefonrehberi.entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Repository
public class PersonDaoImpl implements PersonDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Person findById(Integer id) {
        return sessionFactory.getCurrentSession().get(Person.class, id);
    }

    @Override
    public List<Person> findBySubdepartmentSubDepartmentId(int subDepartmentId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = criteriaQuery.from(Person.class);
        Predicate predicateSubDep = criteriaBuilder.equal(root.get("subdepartment").get("subDepartmentId"), subDepartmentId);
        criteriaQuery.select(root).where(predicateSubDep);
        Query<Person> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<Person> findBySubdepartmentDepartmentDepartmentId(int departmentId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = criteriaQuery.from(Person.class);
        Predicate predicateSubDep = criteriaBuilder.equal(root.get("subdepartment").get("department").get("departmentId"), departmentId);
        criteriaQuery.select(root).where(predicateSubDep);
        Query<Person> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Boolean saveOrUpdate(Person person) {
        boolean success = true;
        try {
            sessionFactory.getCurrentSession().merge(person);
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    @Override
    public List<Person> getAllPersons() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = criteriaQuery.from(Person.class);
        criteriaQuery.select(root);
        Query<Person> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public void delete(Person person) {
        if (Objects.nonNull(person)) {
            sessionFactory.getCurrentSession().remove(person);
        }
    }

}
