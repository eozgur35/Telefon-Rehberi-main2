package duzce.bm.mf.telefonrehberi.dao;

import duzce.bm.mf.telefonrehberi.model.Department;
import java.util.List;

public interface DepartmentDao {
    Boolean saveOrUpdate(Department department);
    List<Department> departmentleriYukle();
    Department getById(int id);
    void delete(int id);
}