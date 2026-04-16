package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.DepartmentDao;
import duzce.bm.mf.telefonrehberi.model.Department;
import duzce.bm.mf.telefonrehberi.services.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = RuntimeException.class)
public class DepartmentService implements IDepartmentService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public List<Department> getAllDepartments() {
        return departmentDao.departmentleriYukle();
    }

    @Override
    @Transactional(readOnly = false)
    public void saveDepartment(Department department) {
        departmentDao.saveOrUpdate(department);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deleteDepartment(int id) {
        try {
            departmentDao.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Department getDepartmentById(int id) {
        return departmentDao.getById(id);
    }
}