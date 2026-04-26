package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.DepartmentDao;
import duzce.bm.mf.telefonrehberi.dto.DepartmentDto;
import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.exception.ResourceNotFoundException;
import duzce.bm.mf.telefonrehberi.services.DepartmentService;
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
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger logger =
            LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Autowired
    DepartmentDao departmentDao;

    public List<DepartmentDto> getAllDepartments() {

        logger.info("Tüm department listesi getiriliyor");

        List<Department> departmentList = departmentDao.getAllDepartments();
        List<DepartmentDto> dtoDepartmentList = new ArrayList<>();

        for (Department department : departmentList) {
            DepartmentDto newDepartmentDto = new DepartmentDto();
            BeanUtils.copyProperties(department, newDepartmentDto);
            dtoDepartmentList.add(newDepartmentDto);
        }

        logger.debug("Toplam department sayısı: {}", dtoDepartmentList.size());

        return dtoDepartmentList;
    }

    public DepartmentDto findById(Integer id) {

        logger.info("Department arama işlemi: id={}", id);

        Department department = departmentDao.findById(id);

        if (Objects.nonNull(department)) {
            DepartmentDto departmentDto = new DepartmentDto();
            BeanUtils.copyProperties(department, departmentDto);

            logger.debug("Department bulundu: {}", departmentDto.getName());

            return departmentDto;
        }

        logger.error("Department bulunamadı: id={}", id);

        throw new ResourceNotFoundException("Department bulunamadı (id: " + id + ")");
    }
}