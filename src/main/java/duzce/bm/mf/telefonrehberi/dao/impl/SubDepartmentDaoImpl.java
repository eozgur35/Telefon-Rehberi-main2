package duzce.bm.mf.telefonrehberi.dao.impl;

import duzce.bm.mf.telefonrehberi.dao.SubDepartmentDao;
import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
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

@Repository
public class SubDepartmentDaoImpl implements SubDepartmentDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<SubDepartment> findByDepartment(Department department){
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<SubDepartment> criteriaQuery = criteriaBuilder.createQuery(SubDepartment.class);
        Root<SubDepartment> root = criteriaQuery.from(SubDepartment.class);
        Predicate predicateDepartment = criteriaBuilder.equal(root.get("department"), department);
        criteriaQuery.select(root).where(predicateDepartment);
        Query<SubDepartment> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<SubDepartment> getAllSubDepartments() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<SubDepartment> criteriaQuery = criteriaBuilder.createQuery(SubDepartment.class);
        Root<SubDepartment> root = criteriaQuery.from(SubDepartment.class);
        criteriaQuery.select(root);
        Query<SubDepartment> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public SubDepartment findById(Integer id) {
        return sessionFactory.getCurrentSession().get(SubDepartment.class, id);
    }
}
