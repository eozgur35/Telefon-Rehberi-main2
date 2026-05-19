package duzce.bm.mf.telefonrehberi.dao;

import duzce.bm.mf.telefonrehberi.entity.Department;

import java.util.List;

public interface DepartmentDao {
    Department findById(Integer id);
    List<Department> getAllDepartments();
    void save(Department department);
    void delete(int id);
}
