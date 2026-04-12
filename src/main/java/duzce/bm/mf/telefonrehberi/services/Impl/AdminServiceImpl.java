package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dto.PersonDto;
import duzce.bm.mf.telefonrehberi.entity.Person;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import duzce.bm.mf.telefonrehberi.repository.PersonRepository;
import duzce.bm.mf.telefonrehberi.repository.SubDepartmentRepository;
import duzce.bm.mf.telefonrehberi.services.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    SubDepartmentRepository subDepartmentRepository;

    public List<PersonDto> getAllPerson() {
        List<Person> personList = personRepository.findAll();
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

    //hoca ekleme
    public void savePerson(PersonDto personDto) {
        Person person = new Person();
        BeanUtils.copyProperties(personDto, person);
        Optional<SubDepartment> optionalSubDepartment = subDepartmentRepository.findById(personDto.getSubDeptId());

        if (optionalSubDepartment.isPresent()) {
            person.setSubdepartment(optionalSubDepartment.get());
        }
        personRepository.save(person);
    }


    public void updatePerson(PersonDto personDto) {
        Person person = personRepository.findByPersonId(personDto.getPersonId());
        BeanUtils.copyProperties(personDto, person);
        Optional<SubDepartment> optionalSubDepartment = subDepartmentRepository.findById(personDto.getSubDeptId());

        if (optionalSubDepartment.isPresent()) {
            person.setSubdepartment(optionalSubDepartment.get());
        }
        personRepository.save(person);
    }

    public boolean deletePerson(int id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<PersonDto> getPersonsBySubDepartmentId(int id) {
        List<Person> personList = personRepository.findBySubdepartmentSubDepartmentId(id);
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
        List<Person> personList = personRepository.findBySubdepartmentDepartmentDepartmentId(id);
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
