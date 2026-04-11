package duzce.bm.mf.telefonrehberi.dto;

import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class PersonDto {
    private String  firstName;
    private String  lastName;
    private String  titleName;
    private String  extensionNumber;
    private String  roomNumber;
    private String  email;
    private SubDepartment subDepartment;
    private Department deptName;

}
