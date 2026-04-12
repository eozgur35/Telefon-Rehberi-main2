package duzce.bm.mf.telefonrehberi.repository;

import duzce.bm.mf.telefonrehberi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByPersonId(int personId);

    List<Person> findBySubdepartmentSubDepartmentId(int subDepartmentId);

    List<Person> findBySubdepartmentDepartmentDepartmentId(int departmentId);

    List<Person> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}

