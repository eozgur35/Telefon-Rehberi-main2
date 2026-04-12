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
import java.util.Optional;

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

    public DepartmentDto findById(Integer id)
    {
        Optional<Department> dbDepartment = departmentRepository.findById(id);

        if(dbDepartment.isPresent())
        {
            DepartmentDto dto = new DepartmentDto();
            BeanUtils.copyProperties(dbDepartment.get(), dto);

            return dto;
        }

        return null;
    }
}
