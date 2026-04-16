package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.DepartmentDao;
import duzce.bm.mf.telefonrehberi.model.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Boolean saveOrUpdate(Department department) {
        try {
            if (department.getDepartmentId() == 0) {
                getCurrentSession().persist(department);
            } else {
                getCurrentSession().merge(department);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Department> departmentleriYukle() {
        return getCurrentSession().createQuery("from Department", Department.class).getResultList();
    }

    @Override
    public Department getById(int id) {
        return getCurrentSession().get(Department.class, id);
    }

    @Override
    public void delete(int id) {
        Department department = getById(id);
        if (department != null) {
            getCurrentSession().remove(department);
        }
    }
}