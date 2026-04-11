package duzce.bm.mf.telefonrehberi.repository;

import duzce.bm.mf.telefonrehberi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    // Alt birime (Bölüm) göre filtreleme
    List<Person> findBySubdepartmentSubDepartmentId(Integer subDepartmentId);

    // Ana birime (Departman) göre tüm personeli getirme
    List<Person> findBySubdepartmentDepartmentDepartmentId(Integer departmentId);

    // Mevcut arama metodun kalabilir
    List<Person> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}

