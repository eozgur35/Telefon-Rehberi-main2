package duzce.bm.mf.telefonrehberi.dao;

import duzce.bm.mf.telefonrehberi.model.SubDepartment;
import java.util.List;

public interface SubDepartmentDao {
    Boolean saveOrUpdate(SubDepartment subDepartment);
    List<SubDepartment> subDepartmentleriYukle();
    SubDepartment getById(int id);
    void delete(int id);

    // Belirli bir fakulteye ait bölümleri listelemek için ekstra metot
    List<SubDepartment> findByDepartmentId(int departmentId);
}