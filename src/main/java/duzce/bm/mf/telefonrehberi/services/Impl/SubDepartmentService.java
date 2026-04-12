package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dto.SubDepartmentDto;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import duzce.bm.mf.telefonrehberi.repository.SubDepartmentRepository;
import duzce.bm.mf.telefonrehberi.services.ISubDepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubDepartmentService implements ISubDepartmentService {

    @Autowired
    SubDepartmentRepository subDepartmentRepository;

    public List<SubDepartmentDto> getAllSubDepartments()
    {
        List<SubDepartment> subDepartments = subDepartmentRepository.findAll();
        List<SubDepartmentDto> subDepartmentDtoList = new ArrayList<>();
        for (SubDepartment dbDepartment : subDepartments)
        {
            SubDepartmentDto subDepartmentDto = new SubDepartmentDto();
            BeanUtils.copyProperties(dbDepartment, subDepartmentDto);
            subDepartmentDto.setDepartmentId(dbDepartment.getDepartment().getDepartmentId());
            subDepartmentDtoList.add(subDepartmentDto);
        }

        return subDepartmentDtoList;
    }

    public String getSubDepartmentNameById(Integer id)
    {
        Optional<SubDepartment> subDepartment = subDepartmentRepository.findById(id);

        if(subDepartment.isPresent())
        {
            return subDepartment.get().getName();
        }
        return null;
    }
}
