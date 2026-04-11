package duzce.bm.mf.telefonrehberi.dto;

import duzce.bm.mf.telefonrehberi.entity.Department;
import duzce.bm.mf.telefonrehberi.entity.SubDepartment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PersonDto {
    private int     personId;
    private String  firstName;
    private String  lastName;
    private String  titleName;
    private String  extensionNumber;
    private String  roomNumber;
    private String  email;
    private SubDepartment subDepartment;
    private Department deptName;

    public PersonDto() {
    }

    public PersonDto(Department deptName, String email, String extensionNumber, String firstName, String lastName, int personId, String roomNumber, SubDepartment subDepartment, String titleName) {
        this.deptName = deptName;
        this.email = email;
        this.extensionNumber = extensionNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personId = personId;
        this.roomNumber = roomNumber;
        this.subDepartment = subDepartment;
        this.titleName = titleName;
    }

    public Department getDeptName() {
        return deptName;
    }

    public void setDeptName(Department deptName) {
        this.deptName = deptName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExtensionNumber() {
        return extensionNumber;
    }

    public void setExtensionNumber(String extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public SubDepartment getSubDepartment() {
        return subDepartment;
    }

    public void setSubDepartment(SubDepartment subDepartment) {
        this.subDepartment = subDepartment;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}
