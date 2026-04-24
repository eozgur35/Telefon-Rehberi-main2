package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.DepartmentDao;
import duzce.bm.mf.telefonrehberi.dto.DepartmentDto;
import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.exception.ResourceNotFoundException;
import duzce.bm.mf.telefonrehberi.services.DepartmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentDao departmentDao;

    public List<DepartmentDto> getAllDepartments() {
        List<Department> departmentList = departmentDao.getAllDepartments();
        List<DepartmentDto> dtoDepartmentList = new ArrayList<>();

        for (Department department : departmentList) {
            DepartmentDto newDepartmentDto = new DepartmentDto();
            BeanUtils.copyProperties(department, newDepartmentDto);
            dtoDepartmentList.add(newDepartmentDto);
        }
        return dtoDepartmentList;
    }

    public DepartmentDto findById(Integer id) {
        Department department = departmentDao.findById(id);

        if(Objects.nonNull(department)) {
            DepartmentDto departmentDto = new DepartmentDto();
            BeanUtils.copyProperties(department, departmentDto);
            return departmentDto;
        }
        throw new ResourceNotFoundException("Department bulunamadı (id: " + id + ")");
    }
}
