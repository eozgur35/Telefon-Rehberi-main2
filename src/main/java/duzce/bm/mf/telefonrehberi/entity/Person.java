package duzce.bm.mf.telefonrehberi.entity;

import jakarta.persistence.*;
import lombok.ToString;

import java.io.Serializable;

@Entity(name = "person")
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

    @ToString.Exclude // Bu alanı loglarda (toString) yoksay
    @Lob
    @Column(name = "photo", columnDefinition="LONGBLOB")
    private byte[] photo;

    public Person(String firstName, String lastName, String extensionNumber, String titleName, String roomNumber, String email, SubDepartment subdepartment, byte[] photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.extensionNumber = extensionNumber;
        this.titleName = titleName;
        this.roomNumber = roomNumber;
        this.email = email;
        this.subdepartment = subdepartment;
        this.photo = photo;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    //aaaaaaa
}
