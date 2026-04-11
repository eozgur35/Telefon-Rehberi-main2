package duzce.bm.mf.telefonrehberi.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;

    private String name;

    private String phones;

    private String faxes;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubDepartment> subDepartments;

    public Department(String faxes, String name, String phones) {
        this.faxes = faxes;
        this.name = name;
        this.phones = phones;
    }

    public Department() {
    }

    public String getFaxes() {
        return faxes;
    }

    public void setFaxes(String faxes) {
        this.faxes = faxes;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public List<SubDepartment> getSubDepartments() {
        return subDepartments;
    }

    public void setSubDepartments(List<SubDepartment> subDepartments) {
        this.subDepartments = subDepartments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }
}
