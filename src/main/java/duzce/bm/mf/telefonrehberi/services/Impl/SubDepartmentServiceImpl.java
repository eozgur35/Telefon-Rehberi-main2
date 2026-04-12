package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dto.SubDepartmentDto;
import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
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

    public List<SubDepartmentDto> getAllSubDepartments(){
        List<SubDepartment> subDepartmentList = subDepartmentRepository.findAll();
        List<SubDepartmentDto> subDepartmentDtoList = new ArrayList<>();

        for (SubDepartment subDepartment : subDepartmentList){
            SubDepartmentDto subDepartmentDto = new SubDepartmentDto();
            BeanUtils.copyProperties(subDepartment, subDepartmentDto);
            subDepartmentDto.setDepartmentId(subDepartment.getDepartment().getDepartmentId());
            subDepartmentDtoList.add(subDepartmentDto);
        }
        return subDepartmentDtoList;
    }

    public List<SubDepartmentDto> getSubDepartmentsByDepartmentId(int id){
        Department department = departmentRepository.findByDepartmentId(id);
        List<SubDepartment> subDepartmentList=subDepartmentRepository.findByDepartment(department);
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
