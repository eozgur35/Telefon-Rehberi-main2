package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.SubDepartmentDao;
import duzce.bm.mf.telefonrehberi.model.SubDepartment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SubDepartmentDaoImpl implements SubDepartmentDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Boolean saveOrUpdate(SubDepartment subDepartment) {
        try {
            if (subDepartment.getSubDepartmentId() == 0) {
                getCurrentSession().persist(subDepartment);
            } else {
                getCurrentSession().merge(subDepartment);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<SubDepartment> subDepartmentleriYukle() {
        return getCurrentSession().createQuery("from SubDepartment", SubDepartment.class).getResultList();
    }

    @Override
    public SubDepartment getById(int id) {
        return getCurrentSession().get(SubDepartment.class, id);
    }

    @Override
    public void delete(int id) {
        SubDepartment sub = getById(id);
        if (sub != null) {
            getCurrentSession().remove(sub);
        }
    }

    @Override
    public List<SubDepartment> findByDepartmentId(int departmentId) {
        return getCurrentSession()
                .createQuery("from SubDepartment s where s.department.departmentId = :deptId", SubDepartment.class)
                .setParameter("deptId", departmentId)
                .getResultList();
    }
}
