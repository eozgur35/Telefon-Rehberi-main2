package duzce.bm.mf.telefonrehberi.services;

import duzce.bm.mf.telefonrehberi.dto.PersonDto;

import java.util.List;

public interface AdminService {
    List<PersonDto> getAllPerson();
    void saveOrUpdatePerson(PersonDto personDto);
    boolean deletePerson(int id);
    List<PersonDto> getPersonsBySubDepartmentId(int id);
    List<PersonDto> getPersonsByDepartmentId(int id);
}
