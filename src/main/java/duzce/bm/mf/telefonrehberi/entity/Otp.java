package duzce.bm.mf.telefonrehberi.entity;

import jakarta.persistence.*;

@Table(name = "password_reset_token")
@Entity
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "otp")
    private String otp;

    @Column(name = "email")
    private String email;

    @Column(name = "verified")
    private boolean verified;

    @Column(name = "users_id")
    private String userId;

    public Otp(String email,String userId,String otp) {
        this.email=email;
        this.userId=userId;
        this.otp=otp;
    }

    public Otp(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }


}
