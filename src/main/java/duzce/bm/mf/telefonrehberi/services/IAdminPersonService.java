package duzce.bm.mf.telefonrehberi.services;

import duzce.bm.mf.telefonrehberi.model.Person;
import java.util.List;

public interface IAdminPersonService {
    List<Person> getAllPerson();
    void savePerson(Person person);
    boolean deletePerson(int id);

    List<Person> findBySubDepartmentId(Integer id);
    List<Person> findByDepartmentId(Integer id);
}