package duzce.bm.mf.telefonrehberi.dto;


public class PersonDto {
    private int personId;
    private String firstName;
    private String lastName;
    private String titleName;
    private String extensionNumber;
    private String roomNumber;
    private String email;
    private String subDeptName;
    private int subDeptId;
    private String deptName;

    public PersonDto() {
    }

    public PersonDto(int personId,String firstName, String lastName, String titleName, String extensionNumber, String roomNumber,String email,String deptName, int subDeptId, String subDeptName) {
        this.deptName = deptName;
        this.email = email;
        this.extensionNumber = extensionNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.personId = personId;
        this.roomNumber = roomNumber;
        this.subDeptId = subDeptId;
        this.subDeptName = subDeptName;
        this.titleName = titleName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
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

    public int getSubDeptId() {
        return subDeptId;
    }

    public void setSubDeptId(int subDeptId) {
        this.subDeptId = subDeptId;
    }

    public String getSubDeptName() {
        return subDeptName;
    }

    public void setSubDeptName(String subDeptName) {
        this.subDeptName = subDeptName;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}