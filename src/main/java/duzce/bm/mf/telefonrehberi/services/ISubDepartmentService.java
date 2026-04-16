package duzce.bm.mf.telefonrehberi.services;

import duzce.bm.mf.telefonrehberi.model.SubDepartment;
import java.util.List;

public interface ISubDepartmentService {
    List<SubDepartment> getAllSubDepartments();
    void saveSubDepartment(SubDepartment subDepartment);
    boolean deleteSubDepartment(int id);
    SubDepartment getSubDepartmentById(int id);
    List<SubDepartment> getSubDepartmentsByDepartmentId(int departmentId);
}