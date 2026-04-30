package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.PersonDao;
import duzce.bm.mf.telefonrehberi.dao.SubDepartmentDao;
import duzce.bm.mf.telefonrehberi.dto.PersonDto;
import duzce.bm.mf.telefonrehberi.entity.Person;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import duzce.bm.mf.telefonrehberi.exception.ResourceNotFoundException;
import duzce.bm.mf.telefonrehberi.services.AdminService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64; // Base64 çevirimi için eklendi
import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    SubDepartmentDao subDepartmentDao;

    @Autowired
    PersonDao personDao;

    public List<PersonDto> getAllPerson() {

        logger.info("Tüm personeller getiriliyor");

        List<Person> personList = personDao.getAllPersons();
        List<PersonDto> personDto = new ArrayList<>();

        for (Person person : personList) {
            PersonDto newPersonDto = new PersonDto();
            BeanUtils.copyProperties(person, newPersonDto);

            newPersonDto.setDeptName(person.getSubdepartment().getDepartment().getName());
            newPersonDto.setSubDeptId(person.getSubdepartment().getSubDepartmentId());
            newPersonDto.setSubDeptName(person.getSubdepartment().getName());

            //Resim cevirme islemi
            if (person.getPhoto() != null) {
                String base64Photo = Base64.getEncoder().encodeToString(person.getPhoto());
                newPersonDto.setPhoto(base64Photo);
            }


            personDto.add(newPersonDto);
        }

        logger.debug("Toplam personel sayısı: {}", personDto.size());

        return personDto;
    }

    public void saveOrUpdatePerson(PersonDto personDto) {

        Person person;

        if (personDto.getPersonId() > 0) {
            person = personDao.findById(personDto.getPersonId());
            if (person == null) {
                throw new ResourceNotFoundException("Güncellenecek person bulunamadı");
            }
        } else {
            person = new Person();
        }


        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setTitleName(personDto.getTitleName());
        person.setExtensionNumber(personDto.getExtensionNumber());
        person.setRoomNumber(personDto.getRoomNumber());
        person.setEmail(personDto.getEmail());

        if (personDto.getPhoto() != null && !personDto.getPhoto().isEmpty()) {
            try {
                byte[] decodedBytes = Base64.getDecoder().decode(personDto.getPhoto());
                person.setPhoto(decodedBytes);
            } catch (IllegalArgumentException e) {
                logger.warn("Resim decode edilemedi: {}", e.getMessage());
            }
        }

        SubDepartment subDepartment = subDepartmentDao.findById(personDto.getSubDeptId());
        if (Objects.isNull(subDepartment)) {
            throw new RuntimeException("Subdepartment bulunamadı.");
        }

        person.setSubdepartment(subDepartment);
        personDao.saveOrUpdate(person);
    }

    public boolean deletePerson(int id) {

        logger.warn("Person silme işlemi başlatıldı: id={}", id);

        Person person = personDao.findById(id);

        if (Objects.nonNull(person)) {
            personDao.delete(person);
            logger.info("Person silindi: id={}", id);
            return true;
        }

        logger.error("Silinecek person bulunamadı: id={}", id);
        throw new ResourceNotFoundException("Silinecek person bulunamadı");
    }

    public List<PersonDto> getPersonsBySubDepartmentId(int id) {

        logger.info("SubDepartment'a göre personeller getiriliyor: id={}", id);

        List<Person> personList = personDao.findBySubdepartmentSubDepartmentId(id);
        List<PersonDto> dtoPerson = new ArrayList<>();

        for (Person person : personList) {
            PersonDto personDto = new PersonDto();
            BeanUtils.copyProperties(person, personDto);

            personDto.setDeptName(person.getSubdepartment().getDepartment().getName());
            personDto.setSubDeptId(person.getSubdepartment().getSubDepartmentId());
            personDto.setSubDeptName(person.getSubdepartment().getName());

            // --- RESIM CEVIRME ISLEMI BASTI ---
            if (person.getPhoto() != null) {
                String base64Photo = Base64.getEncoder().encodeToString(person.getPhoto());
                personDto.setPhoto(base64Photo);
            }
            // --- RESIM CEVIRME ISLEMI BITTI ---

            dtoPerson.add(personDto);
        }

        logger.debug("Bulunan personel sayısı: {}", dtoPerson.size());

        return dtoPerson;
    }

    public List<PersonDto> getPersonsByDepartmentId(int id) {

        logger.info("Department'a göre personeller getiriliyor: id={}", id);

        List<Person> personList = personDao.findBySubdepartmentDepartmentDepartmentId(id);
        List<PersonDto> personDtoList = new ArrayList<>();

        for (Person person : personList) {
            PersonDto personDto = new PersonDto();
            BeanUtils.copyProperties(person, personDto);

            personDto.setDeptName(person.getSubdepartment().getDepartment().getName());
            personDto.setSubDeptId(person.getSubdepartment().getSubDepartmentId());
            personDto.setSubDeptName(person.getSubdepartment().getName());

            // --- RESIM CEVIRME ISLEMI BASTI ---
            if (person.getPhoto() != null) {
                String base64Photo = Base64.getEncoder().encodeToString(person.getPhoto());
                personDto.setPhoto(base64Photo);
            }
            // --- RESIM CEVIRME ISLEMI BITTI ---

            personDtoList.add(personDto);
        }

        logger.debug("Department bazlı personel sayısı: {}", personDtoList.size());

        return personDtoList;
    }
}