package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.DepartmentDao;
import duzce.bm.mf.telefonrehberi.dao.SubDepartmentDao;
import duzce.bm.mf.telefonrehberi.dto.SubDepartmentDto;
import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import duzce.bm.mf.telefonrehberi.exception.ResourceNotFoundException;
import duzce.bm.mf.telefonrehberi.services.SubDepartmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class SubDepartmentServiceImpl implements SubDepartmentService {

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    SubDepartmentDao subDepartmentDao;

    public List<SubDepartmentDto> getAllSubDepartments() {
        List<SubDepartment> subDepartmentList = subDepartmentDao.getAllSubDepartments();
        List<SubDepartmentDto> subDepartmentDtoList = new ArrayList<>();

        for (SubDepartment subDepartment : subDepartmentList) {
            SubDepartmentDto subDepartmentDto = new SubDepartmentDto();
            BeanUtils.copyProperties(subDepartment, subDepartmentDto);
            subDepartmentDto.setDepartmentId(subDepartment.getDepartment().getDepartmentId());
            subDepartmentDtoList.add(subDepartmentDto);
        }
        return subDepartmentDtoList;
    }

    public List<SubDepartmentDto> getSubDepartmentsByDepartmentId(int id) {
        Department department = departmentDao.findById(id);
        if (Objects.isNull(department)) {
            throw new ResourceNotFoundException("Department bulunamadı (id: " + id + ")");
        }
        List<SubDepartment> subDepartmentList = subDepartmentDao.findByDepartment(department);
        if (subDepartmentList.isEmpty()) {
            throw new ResourceNotFoundException("Bu department'a ait subdepartment bulunamadı");
        }
        List<SubDepartmentDto> subDepartmentDtoList = new ArrayList<>();

        for (SubDepartment subDepartment : subDepartmentList) {
            SubDepartmentDto subDepartmentDto = new SubDepartmentDto();
            BeanUtils.copyProperties(subDepartment, subDepartmentDto);
            subDepartmentDto.setDepartmentId(subDepartment.getDepartment().getDepartmentId());

            subDepartmentDtoList.add(subDepartmentDto);
        }
        return subDepartmentDtoList;
    }
}
