package duzce.bm.mf.telefonrehberi.repository;

import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubDepartmentRepository extends JpaRepository<SubDepartment, Integer> {

    // Belirli bir departmana (Birim) ait tüm alt bölümleri getirir
    List<SubDepartment> findByDepartment(Department department);

}