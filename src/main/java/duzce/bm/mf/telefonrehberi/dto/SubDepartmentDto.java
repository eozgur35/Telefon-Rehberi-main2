package duzce.bm.mf.telefonrehberi.dto;

import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.entity.Person;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class SubDepartmentDto {

    private int subDepartmentId;

    private String name;

    private DepartmentDto department;

    private List<PersonDto> persons;

    public SubDepartmentDto(String name, DepartmentDto department, List<PersonDto> persons) {
        this.name = name;
        this.department = department;
        this.persons = persons;
    }
}
