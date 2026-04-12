package duzce.bm.mf.telefonrehberi.services;

import duzce.bm.mf.telefonrehberi.dto.DepartmentDto;

import java.util.List;

public interface IDepartmentService {
    public List<DepartmentDto> getAllDepartments();
    public DepartmentDto findById(Integer id);
}
