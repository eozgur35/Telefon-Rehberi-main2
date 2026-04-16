package duzce.bm.mf.telefonrehberi.services.Impl;

import duzce.bm.mf.telefonrehberi.dao.PersonDao;
import duzce.bm.mf.telefonrehberi.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Spring'in bu sınıfı tanıması için bu notasyon ŞART
public class PersonDaoImpl implements PersonDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Boolean saveOrUpdate(Person person) {
        try {
            if (person.getPersonId() == 0) {
                getCurrentSession().persist(person);
            } else {
                getCurrentSession().merge(person);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Person> personelleriYukle() {
        return getCurrentSession()
                .createQuery("select p from Person p left join fetch p.subDepartment s left join fetch s.department", Person.class)
                .getResultList();
    }
    @Override
    public Person getById(int id) {
        return getCurrentSession().get(Person.class, id);
    }

    @Override
    public void delete(int id) {
        Person person = getById(id);
        if (person != null) {
            getCurrentSession().remove(person);
        }
    }

    @Override
    public List<Person> findBySubDepartmentId(Integer subDepartmentId) {
        return getCurrentSession()
                .createQuery("from Person p where p.subDepartment.subDepartmentId = :id", Person.class)
                .setParameter("id", subDepartmentId)
                .getResultList();
    }

    @Override
    public List<Person> findByDepartmentId(Integer departmentId) {
        return getCurrentSession()
                .createQuery("from Person p where p.subDepartment.department.departmentId = :id", Person.class)
                .setParameter("id", departmentId)
                .getResultList();
    }
}