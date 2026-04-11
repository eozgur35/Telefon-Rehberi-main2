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

    private String name;

    private String phones;

    private String faxes;

    private List<SubDepartment> subDepartments;
}
