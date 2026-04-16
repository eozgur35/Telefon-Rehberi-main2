package duzce.bm.mf.telefonrehberi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "department")
@Data
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private int departmentId;

    @Column(name = "name")
    private String name;

    @Column(name = "phones")
    private String phones;

    @Column(name = "faxes")
    private String faxes;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<SubDepartment> subDepartments;
}