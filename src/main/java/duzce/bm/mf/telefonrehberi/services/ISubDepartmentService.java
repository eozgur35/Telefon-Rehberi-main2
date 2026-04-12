package duzce.bm.mf.telefonrehberi.services;

import duzce.bm.mf.telefonrehberi.dto.PersonDto;
import duzce.bm.mf.telefonrehberi.dto.SubDepartmentDto;

import java.util.List;

public interface ISubDepartmentService {
    public List<SubDepartmentDto> getAllSubDepartments();
    public List<SubDepartmentDto> findByDepartment(Integer id);
}
