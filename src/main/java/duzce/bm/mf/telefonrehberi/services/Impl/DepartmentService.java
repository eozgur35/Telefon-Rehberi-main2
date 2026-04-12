package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dto.DepartmentDto;
import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.repository.DepartmentRepository;
import duzce.bm.mf.telefonrehberi.services.IDepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService implements IDepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public List<DepartmentDto> getAllDepartments()
    {
        List<Department> dbDepartments = departmentRepository.findAll();
        List<DepartmentDto> dtoDepartments = new ArrayList<>();

        for (Department d : dbDepartments)
        {
            DepartmentDto newDepartmentDto = new DepartmentDto();
            BeanUtils.copyProperties(d, newDepartmentDto);
            dtoDepartments.add(newDepartmentDto);
        }

        return dtoDepartments;
    }
}
