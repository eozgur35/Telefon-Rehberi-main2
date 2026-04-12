package duzce.bm.mf.telefonrehberi.services;

import duzce.bm.mf.telefonrehberi.dto.PersonDto;

import java.util.List;

public interface IAdminPersonService {
    public List<PersonDto> getAllPerson();
    public void savePerson(PersonDto personDto);
    public boolean deletePerson(int id);
    public List<PersonDto> findBySubdepartmentSubDepartmentId(Integer id);
    public List<PersonDto> findBySubdepartmentDepartmentDepartmentId(Integer id);
}
