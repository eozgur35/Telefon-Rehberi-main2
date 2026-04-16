package duzce.bm.mf.telefonrehberi.dao;

import duzce.bm.mf.telefonrehberi.model.Person;
import java.util.List;

public interface PersonDao {

    Boolean saveOrUpdate(Person person);

    List<Person> personelleriYukle();

    Person getById(int id);

    void delete(int id);

    List<Person> findBySubDepartmentId(Integer subDepartmentId);

    List<Person> findByDepartmentId(Integer departmentId);
}
