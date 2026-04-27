package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.DepartmentDao;
import duzce.bm.mf.telefonrehberi.dao.SubDepartmentDao;
import duzce.bm.mf.telefonrehberi.dto.SubDepartmentDto;
import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import duzce.bm.mf.telefonrehberi.exception.ResourceNotFoundException;
import duzce.bm.mf.telefonrehberi.services.SubDepartmentService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class SubDepartmentServiceImpl implements SubDepartmentService {

    private static final Logger logger =
            LoggerFactory.getLogger(SubDepartmentServiceImpl.class);

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    SubDepartmentDao subDepartmentDao;

    public List<SubDepartmentDto> getAllSubDepartments() {

        logger.info("Tüm subDepartment listesi getiriliyor");

        List<SubDepartment> subDepartmentList = subDepartmentDao.getAllSubDepartments();
        List<SubDepartmentDto> subDepartmentDtoList = new ArrayList<>();

        for (SubDepartment subDepartment : subDepartmentList) {
            SubDepartmentDto subDepartmentDto = new SubDepartmentDto();
            BeanUtils.copyProperties(subDepartment, subDepartmentDto);

            subDepartmentDto.setDepartmentId(
                    subDepartment.getDepartment().getDepartmentId()
            );

            subDepartmentDtoList.add(subDepartmentDto);
        }

        logger.debug("Toplam subDepartment sayısı: {}", subDepartmentDtoList.size());

        return subDepartmentDtoList;
    }

    public List<SubDepartmentDto> getSubDepartmentsByDepartmentId(int id) {

        logger.info("Department'a göre subDepartment aranıyor: id={}", id);

        Department department = departmentDao.findById(id);

        if (Objects.isNull(department)) {
            logger.error("Department bulunamadı: id={}", id);
            throw new ResourceNotFoundException("Department bulunamadı (id: " + id + ")");
        }

        List<SubDepartment> subDepartmentList =
                subDepartmentDao.findByDepartment(department);

        if (subDepartmentList.isEmpty()) {
            logger.warn("Bu department'a ait subDepartment yok: id={}", id);
            throw new ResourceNotFoundException("Bu department'a ait subdepartment bulunamadı");
        }

        List<SubDepartmentDto> subDepartmentDtoList = new ArrayList<>();

        for (SubDepartment subDepartment : subDepartmentList) {
            SubDepartmentDto subDepartmentDto = new SubDepartmentDto();
            BeanUtils.copyProperties(subDepartment, subDepartmentDto);

            subDepartmentDto.setDepartmentId(
                    subDepartment.getDepartment().getDepartmentId()
            );

            subDepartmentDtoList.add(subDepartmentDto);
        }

        logger.debug("Bulunan subDepartment sayısı: {}", subDepartmentDtoList.size());

        return subDepartmentDtoList;
    }
}