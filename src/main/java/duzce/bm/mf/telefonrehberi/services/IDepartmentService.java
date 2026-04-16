package duzce.bm.mf.telefonrehberi.services;

import duzce.bm.mf.telefonrehberi.model.Department;
import java.util.List;

public interface IDepartmentService {
    List<Department> getAllDepartments();
    void saveDepartment(Department department);
    boolean deleteDepartment(int id);
    Department getDepartmentById(int id);
}