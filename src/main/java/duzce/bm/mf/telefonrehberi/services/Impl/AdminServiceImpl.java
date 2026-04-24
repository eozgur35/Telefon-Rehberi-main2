package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.PersonDao;
import duzce.bm.mf.telefonrehberi.dao.SubDepartmentDao;
import duzce.bm.mf.telefonrehberi.dto.PersonDto;
import duzce.bm.mf.telefonrehberi.entity.Person;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import duzce.bm.mf.telefonrehberi.exception.ResourceNotFoundException;
import duzce.bm.mf.telefonrehberi.services.AdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    SubDepartmentDao subDepartmentDao;

    @Autowired
    PersonDao personDao;

    public List<PersonDto> getAllPerson() {
        List<Person> personList = personDao.getAllPersons();
        List<PersonDto> personDto = new ArrayList<>();

        for (Person person : personList) {
            PersonDto newPersonDto = new PersonDto();
            BeanUtils.copyProperties(person, newPersonDto);
            newPersonDto.setDeptName(person.getSubdepartment().getDepartment().getName());
            newPersonDto.setSubDeptId(person.getSubdepartment().getSubDepartmentId());
            newPersonDto.setSubDeptName(person.getSubdepartment().getName());
            personDto.add(newPersonDto);
        }
        return personDto;
    }

    public void saveOrUpdatePerson(PersonDto personDto) {
        Person person = new Person();
        BeanUtils.copyProperties(personDto, person);
        SubDepartment subDepartment = subDepartmentDao.findById(personDto.getSubDeptId());
        if (Objects.isNull(subDepartment)) {
            throw new RuntimeException("Subdepartment bulunamadı.");
        }
        person.setSubdepartment(subDepartment);
        personDao.saveOrUpdate(person);

    }

    public boolean deletePerson(int id) {
        Person person = personDao.findById(id);
        if(Objects.nonNull(person)) {
            personDao.delete(person);
            return true;
        }
        throw new ResourceNotFoundException("Silinecek person bulunamadı");
    }

    public List<PersonDto> getPersonsBySubDepartmentId(int id) {
        List<Person> personList = personDao.findBySubdepartmentSubDepartmentId(id);
        List<PersonDto> dtoPerson = new ArrayList<>();

        for (Person person : personList) {
            PersonDto personDto = new PersonDto();
            BeanUtils.copyProperties(person, personDto);
            personDto.setDeptName(person.getSubdepartment().getDepartment().getName());
            personDto.setSubDeptId(person.getSubdepartment().getSubDepartmentId());
            personDto.setSubDeptName(person.getSubdepartment().getName());
            dtoPerson.add(personDto);
        }
        return dtoPerson;
    }

    public List<PersonDto> getPersonsByDepartmentId(int id) {
        List<Person> personList = personDao.findBySubdepartmentDepartmentDepartmentId(id);
        List<PersonDto> personDtoList = new ArrayList<>();

        for (Person person : personList) {
            PersonDto personDto = new PersonDto();
            BeanUtils.copyProperties(person, personDto);
            personDto.setDeptName(person.getSubdepartment().getDepartment().getName());
            personDto.setSubDeptId(person.getSubdepartment().getSubDepartmentId());
            personDto.setSubDeptName(person.getSubdepartment().getName());
            personDtoList.add(personDto);
        }
        return personDtoList;
    }
}
