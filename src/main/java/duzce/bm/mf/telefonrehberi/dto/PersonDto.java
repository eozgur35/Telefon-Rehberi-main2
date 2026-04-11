package duzce.bm.mf.telefonrehberi.dto;

import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class PersonDto {
    private int personId;
    private String  firstName;
    private String  lastName;
    private String  titleName;
    private String  extensionNumber;
    private String  roomNumber;
    private String  email;
    private SubDepartment subDepartment;
    private Department deptName;

    public PersonDto(String firstName, String lastName, String titleName, String extensionNumber, String roomNumber, String email, SubDepartment subDepartment, Department deptName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.titleName = titleName;
        this.extensionNumber = extensionNumber;
        this.roomNumber = roomNumber;
        this.email = email;
        this.subDepartment = subDepartment;
        this.deptName = deptName;
    }

}
