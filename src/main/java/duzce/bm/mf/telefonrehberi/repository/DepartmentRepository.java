package duzce.bm.mf.telefonrehberi.repository;

import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    // Belirli bir departmana ait alt bölümleri listeler

}
