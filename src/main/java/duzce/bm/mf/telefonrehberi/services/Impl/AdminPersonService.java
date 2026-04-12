package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dto.PersonDto;
import duzce.bm.mf.telefonrehberi.entity.Person;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import duzce.bm.mf.telefonrehberi.repository.PersonRepository;
import duzce.bm.mf.telefonrehberi.repository.SubDepartmentRepository;
import duzce.bm.mf.telefonrehberi.services.IAdminPersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminPersonService implements IAdminPersonService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    SubDepartmentRepository subDepartmentRepository;

    public List<PersonDto> getAllPerson()
    {
        List<Person> dbPerson = personRepository.findAll();

        List<PersonDto> dtoPerson = new ArrayList<>();

        for(Person p : dbPerson)
        {
            PersonDto newPersonDto = new PersonDto();
            BeanUtils.copyProperties(p, newPersonDto);
            newPersonDto.setDeptName(p.getSubdepartment().getDepartment().getName());
            newPersonDto.setSubDeptId(p.getSubdepartment().getSubDepartmentId());
            newPersonDto.setSubDeptName(p.getSubdepartment().getName());
            dtoPerson.add(newPersonDto);
        }

        return dtoPerson;
    }

    public void savePerson(PersonDto personDto)
    {
        Person p = new Person();

        BeanUtils.copyProperties(personDto, p);
        Optional<SubDepartment> optionalSubDepartment = subDepartmentRepository.findById(personDto.getSubDeptId());

        if(optionalSubDepartment.isPresent()) // isPresent calismazsa !isEmpty kullaniin
        {
            p.setSubdepartment(optionalSubDepartment.get());
        }

        personRepository.save(p);
    }

    public boolean deletePerson(int id)
    {
        if(personRepository.existsById(id))
        {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<PersonDto> findBySubdepartmentSubDepartmentId(Integer id)
    {
        List<Person> dbPerson = personRepository.findBySubdepartmentSubDepartmentId(id);

        List<PersonDto> dtoPerson = new ArrayList<>();

        for(Person p : dbPerson)
        {
            PersonDto newPersonDto = new PersonDto();
            BeanUtils.copyProperties(p, newPersonDto);
            newPersonDto.setDeptName(p.getSubdepartment().getDepartment().getName());
            newPersonDto.setSubDeptId(p.getSubdepartment().getSubDepartmentId());
            newPersonDto.setSubDeptName(p.getSubdepartment().getName());
            dtoPerson.add(newPersonDto);
        }

        return dtoPerson;
    }

    public List<PersonDto> findBySubdepartmentDepartmentDepartmentId(Integer id)
    {
        List<Person> dbPerson = personRepository.findBySubdepartmentDepartmentDepartmentId(id);

        List<PersonDto> dtoPerson = new ArrayList<>();

        for(Person p : dbPerson)
        {
            PersonDto newPersonDto = new PersonDto();
            BeanUtils.copyProperties(p, newPersonDto);
            newPersonDto.setDeptName(p.getSubdepartment().getDepartment().getName());
            newPersonDto.setSubDeptId(p.getSubdepartment().getSubDepartmentId());
            newPersonDto.setSubDeptName(p.getSubdepartment().getName());
            dtoPerson.add(newPersonDto);
        }

        return dtoPerson;
    }
}
