package duzce.bm.mf.telefonrehberi.dao;

import duzce.bm.mf.telefonrehberi.entity.Person;

import java.util.List;

public interface PersonDao {
    Person findById(Integer id);
    List<Person> findBySubdepartmentSubDepartmentId(int subDepartmentId);
    List<Person> findBySubdepartmentDepartmentDepartmentId(int departmentId);
    Boolean saveOrUpdate(Person person);
    List<Person> getAllPersons();
    void delete(Person person);
}
