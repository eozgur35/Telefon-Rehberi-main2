package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.PersonDao;
import duzce.bm.mf.telefonrehberi.model.Person;
import duzce.bm.mf.telefonrehberi.services.IAdminPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = RuntimeException.class)
public class AdminPersonService implements IAdminPersonService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private PersonDao personDao;

    @Override
    public List<Person> getAllPerson() {
        return personDao.personelleriYukle();
    }

    @Override
    @Transactional(readOnly = false)
    public void savePerson(Person person) {
        personDao.saveOrUpdate(person);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean deletePerson(int id) {
        try {
            personDao.delete(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Person> findBySubDepartmentId(Integer id) {
        return personDao.findBySubDepartmentId(id);
    }

    @Override
    public List<Person> findByDepartmentId(Integer id) {
        return personDao.findByDepartmentId(id);
    }
}