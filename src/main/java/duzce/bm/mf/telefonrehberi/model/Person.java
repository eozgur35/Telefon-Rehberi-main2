package duzce.bm.mf.telefonrehberi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "person")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int personId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "title_name")
    private String titleName;

    @Column(name = "email")
    private String email;

    @Column(name = "extension_number")
    private String extensionNumber;

    @Column(name = "room_number")
    private String roomNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subdepartment_id")
    private SubDepartment subDepartment;
}