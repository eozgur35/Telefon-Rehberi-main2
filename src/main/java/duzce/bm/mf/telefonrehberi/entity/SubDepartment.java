package duzce.bm.mf.telefonrehberi.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "subDepartment")
public class SubDepartment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subDepartmentId;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "subdepartment", cascade = CascadeType.ALL)
    private List<Person> persons;

    public SubDepartment() {
    }

    public SubDepartment(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public int getSubDepartmentId() {
        return subDepartmentId;
    }

    public void setSubDepartmentId(int subDepartmentId) {
        this.subDepartmentId = subDepartmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
