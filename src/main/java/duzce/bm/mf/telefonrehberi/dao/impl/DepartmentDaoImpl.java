package duzce.bm.mf.telefonrehberi.dao.impl;

import duzce.bm.mf.telefonrehberi.dao.DepartmentDao;
import duzce.bm.mf.telefonrehberi.entity.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Department> getAllDepartments() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Department> criteriaQuery = criteriaBuilder.createQuery(Department.class);
        Root<Department> root = criteriaQuery.from(Department.class);
        criteriaQuery.select(root);
        Query<Department> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Department findById(Integer id) {
        return sessionFactory.getCurrentSession().get(Department.class, id);
    }
}