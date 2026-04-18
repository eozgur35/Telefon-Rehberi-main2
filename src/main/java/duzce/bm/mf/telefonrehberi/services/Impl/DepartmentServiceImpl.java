package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dto.DepartmentDto;
import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.exception.ResourceNotFoundException;
import duzce.bm.mf.telefonrehberi.repository.DepartmentRepository;
import duzce.bm.mf.telefonrehberi.services.DepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> getAllDepartments() {

        List<Department> departmentList = departmentRepository.findAll();
        List<DepartmentDto> dtoList = new ArrayList<>();

        for (Department department : departmentList) {
            DepartmentDto dto = new DepartmentDto();
            BeanUtils.copyProperties(department, dto);
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public DepartmentDto findById(Integer id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department bulunamadı (id: " + id + ")"));

        DepartmentDto dto = new DepartmentDto();
        BeanUtils.copyProperties(department, dto);

        return dto;
    }
}