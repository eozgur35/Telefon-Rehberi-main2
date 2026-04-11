package duzce.bm.mf.telefonrehberi.dto;

import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DepartmentDto {

    private int departmentId;

    private String name;

    private String phones;

    private String faxes;

    private List<SubDepartment> subDepartments;

    public DepartmentDto(String name, String phones, String faxes, List<SubDepartment> subDepartments) {
        this.name = name;
        this.phones = phones;
        this.faxes = faxes;
        this.subDepartments = subDepartments;
    }
}
