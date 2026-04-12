package duzce.bm.mf.telefonrehberi.services;

import duzce.bm.mf.telefonrehberi.dto.SubDepartmentDto;

import java.util.List;

public interface SubDepartmentService {

    List<SubDepartmentDto> getAllSubDepartments();

    List<SubDepartmentDto> getSubDepartmentsByDepartmentId(int id);

}
