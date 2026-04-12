package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dto.DepartmentDto;
import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.repository.DepartmentRepository;
import duzce.bm.mf.telefonrehberi.services.DepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    public List<DepartmentDto> getAllDepartments() {
        List<Department> departmentList = departmentRepository.findAll();
        List<DepartmentDto> dtoDepartmentList = new ArrayList<>();

        for (Department department : departmentList) {
            DepartmentDto newDepartmentDto = new DepartmentDto();
            BeanUtils.copyProperties(department, newDepartmentDto);
            dtoDepartmentList.add(newDepartmentDto);
        }
        return dtoDepartmentList;
    }

    public DepartmentDto findById(Integer id) {
        Optional<Department> optDepartment = departmentRepository.findById(id);

        if(optDepartment.isPresent()) {
            DepartmentDto departmentDto = new DepartmentDto();
            BeanUtils.copyProperties(optDepartment.get(), departmentDto);
            return departmentDto;
        }
        return null;
    }
}
