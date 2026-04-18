package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dto.PersonDto;
import duzce.bm.mf.telefonrehberi.entity.Person;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import duzce.bm.mf.telefonrehberi.exception.BadRequestException;
import duzce.bm.mf.telefonrehberi.exception.DatabaseException;
import duzce.bm.mf.telefonrehberi.exception.ResourceNotFoundException;
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

    @Override
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

    @Override
    public void savePerson(PersonDto personDto) {

        // ✅ VALIDATION
        if (personDto.getFirstName() == null || personDto.getFirstName().isBlank()) {
            throw new BadRequestException("İsim boş olamaz");
        }

        if (personDto.getLastName() == null || personDto.getLastName().isBlank()) {
            throw new BadRequestException("Soyisim boş olamaz");
        }

        try {
            Person person = new Person();
            BeanUtils.copyProperties(personDto, person);

            SubDepartment subDepartment = subDepartmentRepository.findById(personDto.getSubDeptId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("SubDepartment bulunamadı"));

            person.setSubdepartment(subDepartment);

            personRepository.save(person);

        } catch (Exception e) {
            throw new DatabaseException("Person kaydedilirken hata oluştu");
        }
    }

    @Override
    public void updatePerson(PersonDto personDto) {

        Person person = personRepository.findById(personDto.getPersonId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Person bulunamadı"));

        BeanUtils.copyProperties(personDto, person);

        SubDepartment subDepartment = subDepartmentRepository.findById(personDto.getSubDeptId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("SubDepartment bulunamadı"));

        person.setSubdepartment(subDepartment);

        try {
            personRepository.save(person);
        } catch (Exception e) {
            throw new DatabaseException("Person güncellenirken hata oluştu");
        }
    }

    @Override
    public boolean deletePerson(int id) {

        if (!personRepository.existsById(id)) {
            throw new ResourceNotFoundException("Silinecek person bulunamadı");
        }

        try {
            personRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new DatabaseException("Silme işlemi başarısız");
        }
    }

    @Override
    public List<PersonDto> getPersonsBySubDepartmentId(int id) {

        List<Person> personList =
                personRepository.findBySubdepartmentSubDepartmentId(id);

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

    @Override
    public List<PersonDto> getPersonsByDepartmentId(int id) {

        List<Person> personList =
                personRepository.findBySubdepartmentDepartmentDepartmentId(id);

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