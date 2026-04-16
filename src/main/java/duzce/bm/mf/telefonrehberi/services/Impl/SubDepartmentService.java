package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.SubDepartmentDao;
import duzce.bm.mf.telefonrehberi.model.SubDepartment;
import duzce.bm.mf.telefonrehberi.services.ISubDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = RuntimeException.class)
public class SubDepartmentService implements ISubDepartmentService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SubDepartmentDao subDepartmentDao;

    @Override
    public List<SubDepartment> getAllSubDepartments() {
        return subDepartmentDao.subDepartmentleriYukle();
    }

    @Override
    @Transactional(readOnly = false)
    public void saveSubDepartment(SubDepartment subDepartment) {
        subDepartmentDao.saveOrUpdate(subDepartment);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteSubDepartment(int id) {
        try {
            subDepartmentDao.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public SubDepartment getSubDepartmentById(int id) {
        return subDepartmentDao.getById(id);
    }

    @Override
    public List<SubDepartment> getSubDepartmentsByDepartmentId(int departmentId) {
        return subDepartmentDao.findByDepartmentId(departmentId);
    }
}