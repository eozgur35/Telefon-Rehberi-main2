package duzce.bm.mf.telefonrehberi.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personId;

    private String firstName;

    private String lastName;

    private String extensionNumber;

    private String titleName;

    private String roomNumber;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subdepartment_id")
    private SubDepartment subdepartment;

    public Person(String email, String firstName, String lastName, String roomNumber, String extensionNumber, String titleName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roomNumber = roomNumber;
        this.extensionNumber = extensionNumber;
        this.titleName = titleName;
    }

    public Person() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public SubDepartment getSubdepartment() {
        return subdepartment;
    }

    public void setSubdepartment(SubDepartment subdepartment) {
        this.subdepartment = subdepartment;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getExtensionNumber() {
        return extensionNumber;
    }

    public void setExtensionNumber(String extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}
