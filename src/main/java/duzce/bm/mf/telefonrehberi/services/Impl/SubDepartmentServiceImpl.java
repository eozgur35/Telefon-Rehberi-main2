package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dto.SubDepartmentDto;
import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import duzce.bm.mf.telefonrehberi.exception.ResourceNotFoundException;
import duzce.bm.mf.telefonrehberi.repository.DepartmentRepository;
import duzce.bm.mf.telefonrehberi.repository.SubDepartmentRepository;
import duzce.bm.mf.telefonrehberi.services.SubDepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubDepartmentServiceImpl implements SubDepartmentService {

    @Autowired
    SubDepartmentRepository subDepartmentRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<SubDepartmentDto> getAllSubDepartments() {

        List<SubDepartment> subDepartmentList = subDepartmentRepository.findAll();
        List<SubDepartmentDto> dtoList = new ArrayList<>();

        for (SubDepartment subDepartment : subDepartmentList) {
            SubDepartmentDto dto = new SubDepartmentDto();

            BeanUtils.copyProperties(subDepartment, dto);

            if (subDepartment.getDepartment() != null) {
                dto.setDepartmentId(subDepartment.getDepartment().getDepartmentId());
            }

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public List<SubDepartmentDto> getSubDepartmentsByDepartmentId(int id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department bulunamadı (id: " + id + ")"));

        List<SubDepartment> subDepartmentList =
                subDepartmentRepository.findByDepartment(department);

        if (subDepartmentList.isEmpty()) {
            throw new ResourceNotFoundException("Bu department'a ait subdepartment bulunamadı");
        }

        List<SubDepartmentDto> dtoList = new ArrayList<>();

        for (SubDepartment subDepartment : subDepartmentList) {
            SubDepartmentDto dto = new SubDepartmentDto();

            BeanUtils.copyProperties(subDepartment, dto);

            dto.setDepartmentId(subDepartment.getDepartment().getDepartmentId());

            dtoList.add(dto);
        }

        return dtoList;
    }
}