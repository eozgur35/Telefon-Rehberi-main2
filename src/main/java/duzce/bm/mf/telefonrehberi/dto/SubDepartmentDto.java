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

    private String name;

    private Department department;

    private List<Person> persons;

}
