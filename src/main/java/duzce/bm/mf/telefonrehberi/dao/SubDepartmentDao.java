package duzce.bm.mf.telefonrehberi.dao;

import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;

import java.util.List;

public interface SubDepartmentDao {
    List<SubDepartment> findByDepartment(Department department);
    List<SubDepartment> getAllSubDepartments();
    SubDepartment findById(Integer id);
}
